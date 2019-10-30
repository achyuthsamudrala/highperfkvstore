package org.achyuth.requests;

import java.io.Serializable;

public class GetRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final byte[] key;

    public GetRequest(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return key;
    }
}
