/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 ONOS network topology viewer - PoC version 1.0

 @author Simon Hunt
 */

(function (onos) {
    'use strict';

    var api = onos.api;

    var config = {
            options: {
                layering: true,
                collisionPrevention: true
            },
            XjsonUrl: 'rs/topology/graph',
            jsonUrl: 'network.json',
            iconUrl: {
                device: 'img/device.png',
                host: 'img/host.png',
                pkt: 'img/pkt.png',
                opt: 'img/opt.png'
            },
            mastHeight: 36,
            force: {
                note: 'node.class or link.class is used to differentiate',
                linkDistance: {
                    infra: 240,
                    host: 100
                },
                linkStrength: {
                    infra: 1.0,
                    host: 0.4
                },
                charge: {
                    device: -800,
                    host: -400
                },
                ticksWithoutCollisions: 50,
                marginLR: 20,
                marginTB: 20,
                translate: function() {
                    return 'translate(' +
                        config.force.marginLR + ',' +
                        config.force.marginTB + ')';
                }
            },
            labels: {
                imgPad: 16,
                padLR: 8,
                padTB: 6,
                marginLR: 3,
                marginTB: 2
            },
            icons: {
                w: 32,
                h: 32,
                xoff: -12,
                yoff: -8
            },
            constraints: {
                ypos: {
                    host: 0.15,
                    switch: 0.3,
                    roadm: 0.7
                }
            },
            hostLinkWidth: 1.0,
            mouseOutTimerDelayMs: 120
        },
        view = {},
        network = {},
        selected = {},
        highlighted = null;


    function loadNetworkView() {
        // Hey, here I am, calling something on the ONOS api:
        api.printTime();

        resize();

        d3.json(config.jsonUrl, function (err, data) {
            if (err) {
                alert('Oops! Error reading JSON...\n\n' +
                    'URL: ' + config.jsonUrl + '\n\n' +
                    'Error: ' + err.message);
                return;
            }
            console.log("here is the JSON data...");
            console.log(data);

            network.data = data;
            drawNetwork();
        });

        $(document).on('click', '.select-object', function() {
            // when any object of class "select-object" is clicked...
            // TODO: get a reference to the object via lookup...
            var obj = network.lookup[$(this).data('id')];
            if (obj) {
                selectObject(obj);
            }
            // stop propagation of event (I think) ...
            return false;
        });

        $(window).on('resize', resize);
    }


    // ========================================================

    function drawNetwork() {
        $('#view').empty();

        prepareNodesAndLinks();
        createLayout();
        console.log("\n\nHere is the augmented network object...");
        console.warn(network);
    }

    function prepareNodesAndLinks() {
        network.lookup = {};
        network.nodes = [];
        network.links = [];

        var nw = network.forceWidth,
            nh = network.forceHeight;

        function yPosConstraintForNode(n) {
            return config.constraints.ypos[n.type || 'host'];
        }

        // Note that both 'devices' and 'hosts' get mapped into the nodes array

        // first, the devices...
        network.data.devices.forEach(function(n) {
            var ypc = yPosConstraintForNode(n),
                ix = Math.random() * 0.6 * nw + 0.2 * nw,
                iy = ypc * nh,
                node = {
                    id: n.id,
                    labels: n.labels,
                    class: 'device',
                    icon: 'device',
                    type: n.type,
                    x: ix,
                    y: iy,
                    constraint: {
                        weight: 0.7,
                        y: iy
                    }
                };
            network.lookup[n.id] = node;
            network.nodes.push(node);
        });

        // then, the hosts...
        network.data.hosts.forEach(function(n) {
            var ypc = yPosConstraintForNode(n),
                ix = Math.random() * 0.6 * nw + 0.2 * nw,
                iy = ypc * nh,
                node = {
                    id: n.id,
                    labels: n.labels,
                    class: 'host',
                    icon: 'host',
                    type: n.type,
                    x: ix,
                    y: iy,
                    constraint: {
                        weight: 0.7,
                        y: iy
                    }
                };
            network.lookup[n.id] = node;
            network.nodes.push(node);
        });


        // now, process the explicit links...
        network.data.links.forEach(function(n) {
            var src = network.lookup[n.src],
                dst = network.lookup[n.dst],
                id = src.id + "~" + dst.id;

            var link = {
                class: 'infra',
                id: id,
                type: n.type,
                width: n.linkWidth,
                source: src,
                target: dst,
                strength: config.force.linkStrength.infra
            };
            network.links.push(link);
        });

        // finally, infer host links...
        network.data.hosts.forEach(function(n) {
            var src = network.lookup[n.id],
                dst = network.lookup[n.cp.device],
                id = src.id + "~" + dst.id;

            var link = {
                class: 'host',
                id: id,
                type: 'hostLink',
                width: config.hostLinkWidth,
                source: src,
                target: dst,
                strength: config.force.linkStrength.host
            };
            network.links.push(link);
        });
    }

    function createLayout() {

        var cfg = config.force;

        network.force = d3.layout.force()
            .size([network.forceWidth, network.forceHeight])
            .nodes(network.nodes)
            .links(network.links)
            .linkStrength(function(d) { return cfg.linkStrength[d.class]; })
            .linkDistance(function(d) { return cfg.linkDistance[d.class]; })
            .charge(function(d) { return cfg.charge[d.class]; })
            .on('tick', tick);

        network.svg = d3.select('#view').append('svg')
            .attr('width', view.width)
            .attr('height', view.height)
            .append('g')
            .attr('transform', config.force.translate());
//            .attr('id', 'zoomable')
//            .call(d3.behavior.zoom().on("zoom", zoomRedraw));

//        function zoomRedraw() {
//            d3.select("#zoomable").attr("transform",
//                    "translate(" + d3.event.translate + ")"
//                    + " scale(" + d3.event.scale + ")");
//        }

        // TODO: svg.append('defs') for markers?

        // TODO: move glow/blur stuff to util script
        var glow = network.svg.append('filter')
            .attr('x', '-50%')
            .attr('y', '-50%')
            .attr('width', '200%')
            .attr('height', '200%')
            .attr('id', 'blue-glow');

        glow.append('feColorMatrix')
            .attr('type', 'matrix')
            .attr('values', '0 0 0 0  0 ' +
                '0 0 0 0  0 ' +
                '0 0 0 0  .7 ' +
                '0 0 0 1  0 ');

        glow.append('feGaussianBlur')
            .attr('stdDeviation', 3)
            .attr('result', 'coloredBlur');

        glow.append('feMerge').selectAll('feMergeNode')
            .data(['coloredBlur', 'SourceGraphic'])
            .enter().append('feMergeNode')
            .attr('in', String);

        // TODO: legend (and auto adjust on scroll)
//        $('#view').on('scroll', function() {
//
//        });




        network.link = network.svg.append('g').selectAll('.link')
            .data(network.force.links(), function(d) {return d.id})
            .enter().append('line')
            .attr('class', function(d) {return 'link ' + d.class});


        // == define node drag behavior...
        network.draggedThreshold = d3.scale.linear()
            .domain([0, 0.1])
            .range([5, 20])
            .clamp(true);

        function dragged(d) {
            var threshold = network.draggedThreshold(network.force.alpha()),
                dx = d.oldX - d.px,
                dy = d.oldY - d.py;
            if (Math.abs(dx) >= threshold || Math.abs(dy) >= threshold) {
                d.dragged = true;
            }
            return d.dragged;
        }

        network.drag = d3.behavior.drag()
            .origin(function(d) { return d; })
            .on('dragstart', function(d) {
                d.oldX = d.x;
                d.oldY = d.y;
                d.dragged = false;
                d.fixed |= 2;
            })
            .on('drag', function(d) {
                d.px = d3.event.x;
                d.py = d3.event.y;
                if (dragged(d)) {
                    if (!network.force.alpha()) {
                        network.force.alpha(.025);
                    }
                }
            })
            .on('dragend', function(d) {
                if (!dragged(d)) {
                    selectObject(d, this);
                }
                d.fixed &= ~6;
            });

        $('#view').on('click', function(e) {
            if (!$(e.target).closest('.node').length) {
                deselectObject();
            }
        });


        network.node = network.svg.selectAll('.node')
            .data(network.force.nodes(), function(d) {return d.id})
            .enter().append('g')
            .attr('class', function(d) {
                var cls = 'node ' + d.class;
                if (d.type) {
                    cls += ' ' + d.type;
                }
                return cls;
            })
            .attr('transform', function(d) {
                return translate(d.x, d.y);
            })
            .call(network.drag)
            .on('mouseover', function(d) {
                if (!selected.obj) {
                    if (network.mouseoutTimeout) {
                        clearTimeout(network.mouseoutTimeout);
                        network.mouseoutTimeout = null;
                    }
                    highlightObject(d);
                }
            })
            .on('mouseout', function(d) {
                if (!selected.obj) {
                    if (network.mouseoutTimeout) {
                        clearTimeout(network.mouseoutTimeout);
                        network.mouseoutTimeout = null;
                    }
                    network.mouseoutTimeout = setTimeout(function() {
                        highlightObject(null);
                    }, config.mouseOutTimerDelayMs);
                }
            });

        network.nodeRect = network.node.append('rect')
            .attr('rx', 5)
            .attr('ry', 5);
        // note that width/height are adjusted to fit the label text

        network.node.each(function(d) {
            var node = d3.select(this),
                rect = node.select('rect'),
                icon = iconUrl(d),
                text = node.append('text')
                    // TODO: add label cycle behavior
                    .text(d.id)
                    .attr('dy', '1.1em');

            if (icon) {
                var cfg = config.icons;
                node.append('svg:image')
                    .attr('width', cfg.w)
                    .attr('height', cfg.h)
                    .attr('xlink:href', icon);
                // note, icon relative positioning (x,y) is done after we have
                // adjusted the bounds of the rectangle...
            }

        });

        // this function is scheduled to happen soon after the given thread ends
        setTimeout(function() {
            network.node.each(function(d) {
                // for every node, recompute size, padding, etc. so text fits
                var node = d3.select(this),
                    text = node.selectAll('text'),
                    bounds = {},
                    first = true;

                // NOTE: probably unnecessary code if we only have one line.
                text.each(function() {
                    var box = this.getBBox();
                    if (first || box.x < bounds.x1) {
                        bounds.x1 = box.x;
                    }
                    if (first || box.y < bounds.y1) {
                        bounds.y1 = box.y;
                    }
                    if (first || box.x + box.width < bounds.x2) {
                        bounds.x2 = box.x + box.width;
                    }
                    if (first || box.y + box.height < bounds.y2) {
                        bounds.y2 = box.y + box.height;
                    }
                    first = false;
                }).attr('text-anchor', 'middle');

                var lab = config.labels,
                    oldWidth = bounds.x2 - bounds.x1;

                bounds.x1 -= oldWidth / 2;
                bounds.x2 -= oldWidth / 2;

                bounds.x1 -= (lab.padLR + lab.imgPad);
                bounds.y1 -= lab.padTB;
                bounds.x2 += lab.padLR;
                bounds.y2 += lab.padTB;

                node.select('rect')
                    .attr('x', bounds.x1)
                    .attr('y', bounds.y1)
                    .attr('width', bounds.x2 - bounds.x1)
                    .attr('height', bounds.y2 - bounds.y1);

                node.select('image')
                    .attr('x', bounds.x1 + config.icons.xoff)
                    .attr('y', bounds.y1 + config.icons.yoff);

                d.extent = {
                    left: bounds.x1 - lab.marginLR,
                    right: bounds.x2 + lab.marginLR,
                    top: bounds.y1 - lab.marginTB,
                    bottom: bounds.y2 + lab.marginTB
                };

                d.edge = {
                    left   : new geo.LineSegment(bounds.x1, bounds.y1, bounds.x1, bounds.y2),
                    right  : new geo.LineSegment(bounds.x2, bounds.y1, bounds.x2, bounds.y2),
                    top    : new geo.LineSegment(bounds.x1, bounds.y1, bounds.x2, bounds.y1),
                    bottom : new geo.LineSegment(bounds.x1, bounds.y2, bounds.x2, bounds.y2)
                };

                // ====
            });

            network.numTicks = 0;
            network.preventCollisions = false;
            network.force.start();
            for (var i = 0; i < config.force.ticksWithoutCollisions; i++) {
                network.force.tick();
            }
            network.preventCollisions = true;
            $('#view').css('visibility', 'visible');
        });

    }

    function iconUrl(d) {
        return config.iconUrl[d.icon];
    }

    function translate(x, y) {
        return 'translate(' + x + ',' + y + ')';
    }

    function preventCollisions() {
        var quadtree = d3.geom.quadtree(network.nodes);

        network.nodes.forEach(function(n) {
            var nx1 = n.x + n.extent.left,
                nx2 = n.x + n.extent.right,
                ny1 = n.y + n.extent.top,
                ny2 = n.y + n.extent.bottom;

            quadtree.visit(function(quad, x1, y1, x2, y2) {
                if (quad.point && quad.point !== n) {
                    // check if the rectangles intersect
                    var p = quad.point,
                        px1 = p.x + p.extent.left,
                        px2 = p.x + p.extent.right,
                        py1 = p.y + p.extent.top,
                        py2 = p.y + p.extent.bottom,
                        ix = (px1 <= nx2 && nx1 <= px2 && py1 <= ny2 && ny1 <= py2);
                    if (ix) {
                        var xa1 = nx2 - px1, // shift n left , p right
                            xa2 = px2 - nx1, // shift n right, p left
                            ya1 = ny2 - py1, // shift n up   , p down
                            ya2 = py2 - ny1, // shift n down , p up
                            adj = Math.min(xa1, xa2, ya1, ya2);

                        if (adj == xa1) {
                            n.x -= adj / 2;
                            p.x += adj / 2;
                        } else if (adj == xa2) {
                            n.x += adj / 2;
                            p.x -= adj / 2;
                        } else if (adj == ya1) {
                            n.y -= adj / 2;
                            p.y += adj / 2;
                        } else if (adj == ya2) {
                            n.y += adj / 2;
                            p.y -= adj / 2;
                        }
                    }
                    return ix;
                }
            });

        });
    }

    function tick(e) {
        network.numTicks++;

        if (config.options.layering) {
            // adjust the y-coord of each node, based on y-pos constraints
            network.nodes.forEach(function (n) {
                var z = e.alpha * n.constraint.weight;
                if (!isNaN(n.constraint.y)) {
                    n.y = (n.constraint.y * z + n.y * (1 - z));
                }
            });
        }

        if (config.options.collisionPrevention && network.preventCollisions) {
            preventCollisions();
        }

        // TODO: use intersection technique for source end of link also
        network.link
            .attr('x1', function(d) {
                return d.source.x;
            })
            .attr('y1', function(d) {
                return d.source.y;
            })
            .each(function(d) {
                var x = d.target.x,
                    y = d.target.y,
                    line = new geo.LineSegment(d.source.x, d.source.y, x, y);

                for (var e in d.target.edge) {
                    var ix = line.intersect(d.target.edge[e].offset(x,y));
                    if (ix.in1 && ix.in2) {
                        x = ix.x;
                        y = ix.y;
                        break;
                    }
                }

                d3.select(this)
                    .attr('x2', x)
                    .attr('y2', y);

            });

        network.node
            .attr('transform', function(d) {
                return translate(d.x, d.y);
            });

    }

    //    $('#docs-close').on('click', function() {
    //        deselectObject();
    //        return false;
    //    });

    //    $(document).on('click', '.select-object', function() {
    //        var obj = graph.data[$(this).data('name')];
    //        if (obj) {
    //            selectObject(obj);
    //        }
    //        return false;
    //    });

    function selectObject(obj, el) {
        var node;
        if (el) {
            node = d3.select(el);
        } else {
            network.node.each(function(d) {
                if (d == obj) {
                    node = d3.select(el = this);
                }
            });
        }
        if (!node) return;

        if (node.classed('selected')) {
            deselectObject();
            return;
        }
        deselectObject(false);

        selected = {
            obj : obj,
            el  : el
        };

        highlightObject(obj);

        node.classed('selected', true);

        // TODO animate incoming info pane
        // resize(true);
        // TODO: check bounds of selected node and scroll into view if needed
    }

    function deselectObject(doResize) {
        // Review: logic of 'resize(...)' function.
        if (doResize || typeof doResize == 'undefined') {
            resize(false);
        }
        // deselect all nodes in the network...
        network.node.classed('selected', false);
        selected = {};
        highlightObject(null);
    }

    function highlightObject(obj) {
        if (obj) {
            if (obj != highlighted) {
                // TODO set or clear "inactive" class on nodes, based on criteria
                network.node.classed('inactive', function(d) {
                    //                return (obj !== d &&
                    //                    d.relation(obj.id));
                    return (obj !== d);
                });
                // TODO: same with links
                network.link.classed('inactive', function(d) {
                    return (obj !== d.source && obj !== d.target);
                });
            }
            highlighted = obj;
        } else {
            if (highlighted) {
                // clear the inactive flag (no longer suppressed visually)
                network.node.classed('inactive', false);
                network.link.classed('inactive', false);
            }
            highlighted = null;

        }
    }

    function resize(showDetails) {
        console.log("resize() called...");

        var $details = $('#details');

        if (typeof showDetails == 'boolean') {
            var showingDetails = showDetails;
            // TODO: invoke $details.show() or $details.hide()...
            //        $details[showingDetails ? 'show' : 'hide']();
        }

        view.height = window.innerHeight - config.mastHeight;
        view.width = window.innerWidth;
        $('#view')
            .css('height', view.height + 'px')
            .css('width', view.width + 'px');

        network.forceWidth = view.width - config.force.marginLR;
        network.forceHeight = view.height - config.force.marginTB;
    }

    // ======================================================================
    // register with the UI framework

    api.addView('network', {
        load: loadNetworkView
    });


}(ONOS));
