package org.onlab.onos.store.service.impl;

import org.onlab.onos.store.service.DatabaseException;

/**
 * Exception that indicates a problem with the state machine snapshotting.
 */
@SuppressWarnings("serial")
public class SnapshotException extends DatabaseException {
    public SnapshotException(Throwable t) {
        super(t);
    }
}