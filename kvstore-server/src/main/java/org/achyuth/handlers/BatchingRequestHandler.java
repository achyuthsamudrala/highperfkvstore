package org.achyuth.handlers;

import org.achyuth.memory.SSCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BatchingRequestHandler implements RequestHandler {


    private final Map<byte[], byte[]> currentActiveBatch;
    private final SSCollection ssCollection;
    private final int batchSize; /* This is the maximum size of each individual segment in org.achyuth.memory */

    public BatchingRequestHandler(SSCollection ssCollection, int batchSize) {
        this.currentActiveBatch = new ConcurrentHashMap<>(batchSize);
        this.ssCollection = ssCollection;
        this.batchSize = batchSize;
    }

    @Override
    public byte[] get(byte[] key) throws Exception {
        if (currentActiveBatch.containsKey(key))
            return currentActiveBatch.get(key);
        else return ssCollection.get(key);
    }

    @Override
    public void set(byte[] key, byte[] value) throws Exception {
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

    private synchronized void createNewSortedSegment() throws Exception {
        Map<byte[], byte[]> lastCompleteSegment = Map.copyOf(currentActiveBatch);
        ssCollection.addSegment(lastCompleteSegment);
        currentActiveBatch.clear();
    }

    private boolean isFull() {
        return currentActiveBatch.size() == batchSize;
    }

    private boolean hasCapacity() {
        return currentActiveBatch.size() < batchSize;
    }

    Map<byte[], byte[]> getCurrentActiveBatch() {
        return currentActiveBatch;
    }
}
