// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template const_serializer.java
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
import org.projectfloodlight.openflow.protocol.OFErrorType;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.hash.PrimitiveSink;

public class OFErrorTypeSerializerVer13 {

    public final static short HELLO_FAILED_VAL = (short) 0x0;
    public final static short BAD_REQUEST_VAL = (short) 0x1;
    public final static short BAD_ACTION_VAL = (short) 0x2;
    public final static short BAD_INSTRUCTION_VAL = (short) 0x3;
    public final static short BAD_MATCH_VAL = (short) 0x4;
    public final static short FLOW_MOD_FAILED_VAL = (short) 0x5;
    public final static short GROUP_MOD_FAILED_VAL = (short) 0x6;
    public final static short PORT_MOD_FAILED_VAL = (short) 0x7;
    public final static short TABLE_MOD_FAILED_VAL = (short) 0x8;
    public final static short QUEUE_OP_FAILED_VAL = (short) 0x9;
    public final static short SWITCH_CONFIG_FAILED_VAL = (short) 0xa;
    public final static short ROLE_REQUEST_FAILED_VAL = (short) 0xb;
    public final static short METER_MOD_FAILED_VAL = (short) 0xc;
    public final static short TABLE_FEATURES_FAILED_VAL = (short) 0xd;
    public final static short EXPERIMENTER_VAL = (short) 0xffff;

    public static OFErrorType readFrom(ChannelBuffer bb) throws OFParseError {
        try {
            return ofWireValue(bb.readShort());
        } catch (IllegalArgumentException e) {
            throw new OFParseError(e);
        }
    }

    public static void writeTo(ChannelBuffer bb, OFErrorType e) {
        bb.writeShort(toWireValue(e));
    }

    public static void putTo(OFErrorType e, PrimitiveSink sink) {
        sink.putShort(toWireValue(e));
    }

    public static OFErrorType ofWireValue(short val) {
        switch(val) {
            case HELLO_FAILED_VAL:
                return OFErrorType.HELLO_FAILED;
            case BAD_REQUEST_VAL:
                return OFErrorType.BAD_REQUEST;
            case BAD_ACTION_VAL:
                return OFErrorType.BAD_ACTION;
            case BAD_INSTRUCTION_VAL:
                return OFErrorType.BAD_INSTRUCTION;
            case BAD_MATCH_VAL:
                return OFErrorType.BAD_MATCH;
            case FLOW_MOD_FAILED_VAL:
                return OFErrorType.FLOW_MOD_FAILED;
            case GROUP_MOD_FAILED_VAL:
                return OFErrorType.GROUP_MOD_FAILED;
            case PORT_MOD_FAILED_VAL:
                return OFErrorType.PORT_MOD_FAILED;
            case TABLE_MOD_FAILED_VAL:
                return OFErrorType.TABLE_MOD_FAILED;
            case QUEUE_OP_FAILED_VAL:
                return OFErrorType.QUEUE_OP_FAILED;
            case SWITCH_CONFIG_FAILED_VAL:
                return OFErrorType.SWITCH_CONFIG_FAILED;
            case ROLE_REQUEST_FAILED_VAL:
                return OFErrorType.ROLE_REQUEST_FAILED;
            case METER_MOD_FAILED_VAL:
                return OFErrorType.METER_MOD_FAILED;
            case TABLE_FEATURES_FAILED_VAL:
                return OFErrorType.TABLE_FEATURES_FAILED;
            case EXPERIMENTER_VAL:
                return OFErrorType.EXPERIMENTER;
            default:
                throw new IllegalArgumentException("Illegal wire value for type OFErrorType in version 1.3: " + val);
        }
    }


    public static short toWireValue(OFErrorType e) {
        switch(e) {
            case HELLO_FAILED:
                return HELLO_FAILED_VAL;
            case BAD_REQUEST:
                return BAD_REQUEST_VAL;
            case BAD_ACTION:
                return BAD_ACTION_VAL;
            case BAD_INSTRUCTION:
                return BAD_INSTRUCTION_VAL;
            case BAD_MATCH:
                return BAD_MATCH_VAL;
            case FLOW_MOD_FAILED:
                return FLOW_MOD_FAILED_VAL;
            case GROUP_MOD_FAILED:
                return GROUP_MOD_FAILED_VAL;
            case PORT_MOD_FAILED:
                return PORT_MOD_FAILED_VAL;
            case TABLE_MOD_FAILED:
                return TABLE_MOD_FAILED_VAL;
            case QUEUE_OP_FAILED:
                return QUEUE_OP_FAILED_VAL;
            case SWITCH_CONFIG_FAILED:
                return SWITCH_CONFIG_FAILED_VAL;
            case ROLE_REQUEST_FAILED:
                return ROLE_REQUEST_FAILED_VAL;
            case METER_MOD_FAILED:
                return METER_MOD_FAILED_VAL;
            case TABLE_FEATURES_FAILED:
                return TABLE_FEATURES_FAILED_VAL;
            case EXPERIMENTER:
                return EXPERIMENTER_VAL;
            default:
                throw new IllegalArgumentException("Illegal enum value for type OFErrorType in version 1.3: " + e);
        }
    }

}
