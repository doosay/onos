// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template of_virtual_class.java
// Do not modify

package org.projectfloodlight.openflow.protocol.ver13;

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
import org.jboss.netty.buffer.ChannelBuffer;

abstract class OFStatsReplyVer13 {
    // version: 1.3
    final static byte WIRE_VERSION = 4;
    final static int MINIMUM_LENGTH = 16;


    public final static OFStatsReplyVer13.Reader READER = new Reader();

    static class Reader implements OFMessageReader<OFStatsReply> {
        @Override
        public OFStatsReply readFrom(ChannelBuffer bb) throws OFParseError {
            if(bb.readableBytes() < MINIMUM_LENGTH)
                return null;
            int start = bb.readerIndex();
            // fixed value property version == 4
            byte version = bb.readByte();
            if(version != (byte) 0x4)
                throw new OFParseError("Wrong version: Expected=OFVersion.OF_13(4), got="+version);
            // fixed value property type == 19
            byte type = bb.readByte();
            if(type != (byte) 0x13)
                throw new OFParseError("Wrong type: Expected=OFType.STATS_REPLY(19), got="+type);
            int length = U16.f(bb.readShort());
            if(length < MINIMUM_LENGTH)
                throw new OFParseError("Wrong length: Expected to be >= " + MINIMUM_LENGTH + ", was: " + length);
            U32.f(bb.readInt());
            short statsType = bb.readShort();
            bb.readerIndex(start);
            switch(statsType) {
               case (short) 0x2:
                   // discriminator value OFStatsType.AGGREGATE=2 for class OFAggregateStatsReplyVer13
                   return OFAggregateStatsReplyVer13.READER.readFrom(bb);
               case (short) 0xffff:
                   // discriminator value OFStatsType.EXPERIMENTER=65535 for class OFExperimenterStatsReplyVer13
                   return OFExperimenterStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x0:
                   // discriminator value OFStatsType.DESC=0 for class OFDescStatsReplyVer13
                   return OFDescStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x1:
                   // discriminator value OFStatsType.FLOW=1 for class OFFlowStatsReplyVer13
                   return OFFlowStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x4:
                   // discriminator value OFStatsType.PORT=4 for class OFPortStatsReplyVer13
                   return OFPortStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x5:
                   // discriminator value OFStatsType.QUEUE=5 for class OFQueueStatsReplyVer13
                   return OFQueueStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x3:
                   // discriminator value OFStatsType.TABLE=3 for class OFTableStatsReplyVer13
                   return OFTableStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x7:
                   // discriminator value OFStatsType.GROUP_DESC=7 for class OFGroupDescStatsReplyVer13
                   return OFGroupDescStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x6:
                   // discriminator value OFStatsType.GROUP=6 for class OFGroupStatsReplyVer13
                   return OFGroupStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x8:
                   // discriminator value OFStatsType.GROUP_FEATURES=8 for class OFGroupFeaturesStatsReplyVer13
                   return OFGroupFeaturesStatsReplyVer13.READER.readFrom(bb);
               case (short) 0xa:
                   // discriminator value OFStatsType.METER_CONFIG=10 for class OFMeterConfigStatsReplyVer13
                   return OFMeterConfigStatsReplyVer13.READER.readFrom(bb);
               case (short) 0xb:
                   // discriminator value OFStatsType.METER_FEATURES=11 for class OFMeterFeaturesStatsReplyVer13
                   return OFMeterFeaturesStatsReplyVer13.READER.readFrom(bb);
               case (short) 0x9:
                   // discriminator value OFStatsType.METER=9 for class OFMeterStatsReplyVer13
                   return OFMeterStatsReplyVer13.READER.readFrom(bb);
               case (short) 0xd:
                   // discriminator value OFStatsType.PORT_DESC=13 for class OFPortDescStatsReplyVer13
                   return OFPortDescStatsReplyVer13.READER.readFrom(bb);
               case (short) 0xc:
                   // discriminator value OFStatsType.TABLE_FEATURES=12 for class OFTableFeaturesStatsReplyVer13
                   return OFTableFeaturesStatsReplyVer13.READER.readFrom(bb);
               default:
                   throw new OFParseError("Unknown value for discriminator statsType of class OFStatsReplyVer13: " + statsType);
            }
        }
    }
}
