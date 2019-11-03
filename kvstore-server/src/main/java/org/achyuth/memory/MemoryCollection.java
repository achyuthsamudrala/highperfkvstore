package org.achyuth.memory;

import org.achyuth.filesystem.StoredDataHandler;
import org.apache.log4j.Logger;

import java.util.*;

public class MemoryCollection {

    private final int capacity;
    private final Map<String, String> currentActiveBatch;
    private static final Logger LOGGER = Logger.getLogger(MemoryCollection.class);

    public MemoryCollection(int capacity) {
        this.capacity = capacity;
        this.currentActiveBatch = new HashMap<>(capacity);
    }

    public boolean containsKey(String key) {
        return currentActiveBatch.containsKey(key);
    }

    public String get(String key) {
        return currentActiveBatch.getOrDefault(key, null);
    }

    public void set(String key, String value, StoredDataHandler storedDataHandler) {
        currentActiveBatch.put(key, value);
        if (isFull()) {
            Map<String, String> cloneOfActiveCollection = shallowCopy();
            clear();
            storedDataHandler.persist(cloneOfActiveCollection);
        }
    }

    private boolean isFull() {
        return currentActiveBatch.size() == capacity;
    }

    private void clear() {
        currentActiveBatch.clear();
    }

    private Map<String, String> shallowCopy() {
        return new HashMap<>(currentActiveBatch);
    }
}
