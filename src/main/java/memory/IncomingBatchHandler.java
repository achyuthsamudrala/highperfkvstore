package memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IncomingBatchHandler {

    private static final int CAPACITY = 10000; /* This is the maximum size of each individual segment in memory */

    private final Map<byte[], byte[]> keyValues;
    private final SSCollection ssCollection;

    public IncomingBatchHandler(SSCollection ssCollection) {
        this.keyValues = new ConcurrentHashMap<>(CAPACITY);
        this.ssCollection = ssCollection;
    }

    public void set(byte[] key, byte[] value) {
        if (hasCapacity()) {
            keyValues.put(key, value);
        }
        if (isFull()) {
            SortedSegment lastCompleteSegment = convertToSortedSegment(Map.copyOf(keyValues));
            ssCollection.addSegment(lastCompleteSegment);
            keyValues.clear();
            keyValues.put(key, value);
        }
    }

    private boolean isFull() {
        return keyValues.size() == CAPACITY;
    }

    private boolean hasCapacity() {
        return keyValues.size() < CAPACITY;
    }

    public Map<byte[], byte[]> getCurrentActiveBatch() {
        return keyValues;
    }

    private SortedSegment convertToSortedSegment(Map<byte[], byte[]> keyValues) {
        /* Add logic to convert into sorted segment*/
        return new SortedSegment(keyValues);
    }
}
