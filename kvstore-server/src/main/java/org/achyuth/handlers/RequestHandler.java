package org.achyuth.handlers;

import org.achyuth.memory.SSCollection;

public interface RequestHandler {

    byte[] get(byte[] key) throws Exception;
    void set(byte[] key, byte[] value) throws Exception;
    SSCollection getSnapshot();
}
