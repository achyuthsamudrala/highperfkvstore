package memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IncomingBatch {

    private static final int CAPACITY = 10000; /* This is the maximum size of each individual segment in memory */
    private final Map<byte[], byte[]> keyValues;

    public IncomingBatch() {
        keyValues = new ConcurrentHashMap<byte[], byte[]>(CAPACITY);
    }

    public boolean set(byte[] key, byte[] value) {
        if (hasCapacity()) {
            keyValues.put(key, value);
            return true;
        } else return false;
    }

    public boolean hasCapacity() {
        return keyValues.size() < CAPACITY;
    }

    public Map<byte[], byte[]> getKeyValues() {
        return keyValues;
    }
}
