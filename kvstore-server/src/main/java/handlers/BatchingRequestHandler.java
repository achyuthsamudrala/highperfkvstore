package handlers;

import memory.SSCollection;
import memory.SortedSegment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BatchingRequestHandler implements RequestHandler {

    private static final int CAPACITY = 10000; /* This is the maximum size of each individual segment in memory */

    private final Map<byte[], byte[]> currentActiveBatch;
    private final SSCollection ssCollection;

    public BatchingRequestHandler(SSCollection ssCollection) {
        this.currentActiveBatch = new ConcurrentHashMap<>(CAPACITY);
        this.ssCollection = ssCollection;
    }

    @Override
    public byte[] get(byte[] key) {
        if (currentActiveBatch.containsKey(key))
            return currentActiveBatch.get(key);
        else return ssCollection.get(key);
    }

    @Override
    public void set(byte[] key, byte[] value) {
        if (hasCapacity()) {
            currentActiveBatch.put(key, value);
            if (isFull())
                createNewSortedSegment();
        } else {
            createNewSortedSegment();
            currentActiveBatch.put(key, value);
        }
    }

    @Override
    public SSCollection getSnapshot() {
        return null; //TODO
    }

    private void createNewSortedSegment() {
        SortedSegment lastCompleteSegment = convertToSortedSegment(Map.copyOf(currentActiveBatch));
        ssCollection.addSegment(lastCompleteSegment);
        currentActiveBatch.clear();
    }

    private boolean isFull() {
        return currentActiveBatch.size() == CAPACITY;
    }

    private boolean hasCapacity() {
        return currentActiveBatch.size() < CAPACITY;
    }

    public Map<byte[], byte[]> getCurrentActiveBatch() {
        return currentActiveBatch;
    }

    private SortedSegment convertToSortedSegment(Map<byte[], byte[]> keyValues) {
        /* TODO Add logic to convert into sorted segment*/
        return new SortedSegment(keyValues);
    }
}
