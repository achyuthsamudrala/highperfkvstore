package org.achyuth.requests;

import java.io.Serializable;

public class SetRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final byte[] key;
    private final byte[] value;

    public SetRequest(byte[] key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getValue() {
        return value;
    }
}
