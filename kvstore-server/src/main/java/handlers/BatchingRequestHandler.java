package handlers;

import memory.SSCollection;
import memory.SortedSegment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BatchingRequestHandler implements RequestHandler {

    private static final int CAPACITY = 10000; /* This is the maximum size of each individual segment in memory */

    private final Map<byte[], byte[]> keyValues;
    private final SSCollection ssCollection;

    public BatchingRequestHandler(SSCollection ssCollection) {
        this.keyValues = new ConcurrentHashMap<>(CAPACITY);
        this.ssCollection = ssCollection;
    }

    @Override
    public byte[] get(byte[] key) {
        return null; //TODO
    }

    @Override
    public void set(byte[] key, byte[] value) {
        if (hasCapacity()) {
            keyValues.put(key, value);
            if (isFull())
                createNewSortedSegment();
        } else {
            createNewSortedSegment();
            keyValues.put(key, value);
        }
    }

    @Override
    public SSCollection getSnapshot() {
        return null; //TODO
    }

    private void createNewSortedSegment() {
        SortedSegment lastCompleteSegment = convertToSortedSegment(Map.copyOf(keyValues));
        ssCollection.addSegment(lastCompleteSegment);
        keyValues.clear();
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
        /* TODO Add logic to convert into sorted segment*/
        return new SortedSegment(keyValues);
    }
}
