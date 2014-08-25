// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template of_class.java
// Do not modify

package org.projectfloodlight.openflow.protocol.ver11;

import org.projectfloodlight.openflow.protocol.*;
import org.projectfloodlight.openflow.protocol.action.*;
import org.projectfloodlight.openflow.protocol.actionid.*;
import org.projectfloodlight.openflow.protocol.bsntlv.*;
import org.projectfloodlight.openflow.protocol.errormsg.*;
import org.projectfloodlight.openflow.protocol.meterband.*;
import org.projectfloodlight.openflow.protocol.instruction.*;
import org.projectfloodlight.openflow.protocol.instructionid.*;
import org.projectfloodlight.openflow.protocol.match.*;
import org.projectfloodlight.openflow.protocol.oxm.*;
import org.projectfloodlight.openflow.protocol.queueprop.*;
import org.projectfloodlight.openflow.types.*;
import org.projectfloodlight.openflow.util.*;
import org.projectfloodlight.openflow.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.hash.PrimitiveSink;
import com.google.common.hash.Funnel;

class OFBsnGetMirroringRequestVer11 implements OFBsnGetMirroringRequest {
    private static final Logger logger = LoggerFactory.getLogger(OFBsnGetMirroringRequestVer11.class);
    // version: 1.1
    final static byte WIRE_VERSION = 2;
    final static int LENGTH = 20;

        private final static long DEFAULT_XID = 0x0L;
        private final static short DEFAULT_REPORT_MIRROR_PORTS = (short) 0x0;

    // OF message fields
    private final long xid;
    private final short reportMirrorPorts;
//
    // Immutable default instance
    final static OFBsnGetMirroringRequestVer11 DEFAULT = new OFBsnGetMirroringRequestVer11(
        DEFAULT_XID, DEFAULT_REPORT_MIRROR_PORTS
    );

    // package private constructor - used by readers, builders, and factory
    OFBsnGetMirroringRequestVer11(long xid, short reportMirrorPorts) {
        this.xid = xid;
        this.reportMirrorPorts = reportMirrorPorts;
    }

    // Accessors for OF message fields
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0x4L;
    }

    @Override
    public short getReportMirrorPorts() {
        return reportMirrorPorts;
    }



    public OFBsnGetMirroringRequest.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFBsnGetMirroringRequest.Builder {
        final OFBsnGetMirroringRequestVer11 parentMessage;

        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean reportMirrorPortsSet;
        private short reportMirrorPorts;

        BuilderWithParent(OFBsnGetMirroringRequestVer11 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFBsnGetMirroringRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0x4L;
    }

    @Override
    public short getReportMirrorPorts() {
        return reportMirrorPorts;
    }

    @Override
    public OFBsnGetMirroringRequest.Builder setReportMirrorPorts(short reportMirrorPorts) {
        this.reportMirrorPorts = reportMirrorPorts;
        this.reportMirrorPortsSet = true;
        return this;
    }


        @Override
        public OFBsnGetMirroringRequest build() {
                long xid = this.xidSet ? this.xid : parentMessage.xid;
                short reportMirrorPorts = this.reportMirrorPortsSet ? this.reportMirrorPorts : parentMessage.reportMirrorPorts;

                //
                return new OFBsnGetMirroringRequestVer11(
                    xid,
                    reportMirrorPorts
                );
        }

    }

    static class Builder implements OFBsnGetMirroringRequest.Builder {
        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean reportMirrorPortsSet;
        private short reportMirrorPorts;

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFBsnGetMirroringRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0x4L;
    }

    @Override
    public short getReportMirrorPorts() {
        return reportMirrorPorts;
    }

    @Override
    public OFBsnGetMirroringRequest.Builder setReportMirrorPorts(short reportMirrorPorts) {
        this.reportMirrorPorts = reportMirrorPorts;
        this.reportMirrorPortsSet = true;
        return this;
    }
//
        @Override
        public OFBsnGetMirroringRequest build() {
            long xid = this.xidSet ? this.xid : DEFAULT_XID;
            short reportMirrorPorts = this.reportMirrorPortsSet ? this.reportMirrorPorts : DEFAULT_REPORT_MIRROR_PORTS;


            return new OFBsnGetMirroringRequestVer11(
                    xid,
                    reportMirrorPorts
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFBsnGetMirroringRequest> {
        @Override
        public OFBsnGetMirroringRequest readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            // fixed value property version == 2
            byte version = bb.readByte();
            if(version != (byte) 0x2)
                throw new OFParseError("Wrong version: Expected=OFVersion.OF_11(2), got="+version);
            // fixed value property type == 4
            byte type = bb.readByte();
            if(type != (byte) 0x4)
                throw new OFParseError("Wrong type: Expected=OFType.EXPERIMENTER(4), got="+type);
            int length = U16.f(bb.readShort());
            if(length != 20)
                throw new OFParseError("Wrong length: Expected=20(20), got="+length);
            if(bb.readableBytes() + (bb.readerIndex() - start) < length) {
                // Buffer does not have all data yet
                bb.readerIndex(start);
                return null;
            }
            if(logger.isTraceEnabled())
                logger.trace("readFrom - length={}", length);
            long xid = U32.f(bb.readInt());
            // fixed value property experimenter == 0x5c16c7L
            int experimenter = bb.readInt();
            if(experimenter != 0x5c16c7)
                throw new OFParseError("Wrong experimenter: Expected=0x5c16c7L(0x5c16c7L), got="+experimenter);
            // fixed value property subtype == 0x4L
            int subtype = bb.readInt();
            if(subtype != 0x4)
                throw new OFParseError("Wrong subtype: Expected=0x4L(0x4L), got="+subtype);
            short reportMirrorPorts = U8.f(bb.readByte());
            // pad: 3 bytes
            bb.skipBytes(3);

            OFBsnGetMirroringRequestVer11 bsnGetMirroringRequestVer11 = new OFBsnGetMirroringRequestVer11(
                    xid,
                      reportMirrorPorts
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", bsnGetMirroringRequestVer11);
            return bsnGetMirroringRequestVer11;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFBsnGetMirroringRequestVer11Funnel FUNNEL = new OFBsnGetMirroringRequestVer11Funnel();
    static class OFBsnGetMirroringRequestVer11Funnel implements Funnel<OFBsnGetMirroringRequestVer11> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFBsnGetMirroringRequestVer11 message, PrimitiveSink sink) {
            // fixed value property version = 2
            sink.putByte((byte) 0x2);
            // fixed value property type = 4
            sink.putByte((byte) 0x4);
            // fixed value property length = 20
            sink.putShort((short) 0x14);
            sink.putLong(message.xid);
            // fixed value property experimenter = 0x5c16c7L
            sink.putInt(0x5c16c7);
            // fixed value property subtype = 0x4L
            sink.putInt(0x4);
            sink.putShort(message.reportMirrorPorts);
            // skip pad (3 bytes)
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFBsnGetMirroringRequestVer11> {
        @Override
        public void write(ChannelBuffer bb, OFBsnGetMirroringRequestVer11 message) {
            // fixed value property version = 2
            bb.writeByte((byte) 0x2);
            // fixed value property type = 4
            bb.writeByte((byte) 0x4);
            // fixed value property length = 20
            bb.writeShort((short) 0x14);
            bb.writeInt(U32.t(message.xid));
            // fixed value property experimenter = 0x5c16c7L
            bb.writeInt(0x5c16c7);
            // fixed value property subtype = 0x4L
            bb.writeInt(0x4);
            bb.writeByte(U8.t(message.reportMirrorPorts));
            // pad: 3 bytes
            bb.writeZero(3);


        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFBsnGetMirroringRequestVer11(");
        b.append("xid=").append(xid);
        b.append(", ");
        b.append("reportMirrorPorts=").append(reportMirrorPorts);
        b.append(")");
        return b.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OFBsnGetMirroringRequestVer11 other = (OFBsnGetMirroringRequestVer11) obj;

        if( xid != other.xid)
            return false;
        if( reportMirrorPorts != other.reportMirrorPorts)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime *  (int) (xid ^ (xid >>> 32));
        result = prime * result + reportMirrorPorts;
        return result;
    }

}
