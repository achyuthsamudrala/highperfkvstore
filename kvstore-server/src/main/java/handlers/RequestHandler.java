package handlers;

import memory.SSCollection;

public interface RequestHandler {

    byte[] get(byte[] key);
    void set(byte[] key, byte[] value);
    SSCollection getSnapshot();
}
