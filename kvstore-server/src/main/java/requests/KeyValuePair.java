package requests;

import java.io.Serializable;

public class KeyValuePair implements Serializable {

    private final byte[] key;
    private final byte[] value;

    public KeyValuePair(byte[] key, byte[] value) {
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
