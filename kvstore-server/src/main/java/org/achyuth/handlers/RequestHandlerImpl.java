package org.achyuth.handlers;

import org.achyuth.exceptions.KeyNotFoundException;
import org.achyuth.filesystem.CommitLogHandler;
import org.achyuth.filesystem.StoredDataHandler;
import org.achyuth.memory.MemoryCollection;

public class RequestHandlerImpl implements RequestHandler {

    private final MemoryCollection inMemoryCollection;
    private final CommitLogHandler commitLogHandler;
    private final StoredDataHandler storedDataHandler;

    public RequestHandlerImpl(MemoryCollection collection,
                              CommitLogHandler commitLogHandler, StoredDataHandler storedDataHandler) {
        this.inMemoryCollection = collection;
        this.commitLogHandler = commitLogHandler;
        this.storedDataHandler = storedDataHandler;
    }

    @Override
    public String get(String key) throws Exception {
        if (inMemoryCollection.containsKey(key))
            return inMemoryCollection.get(key);
        String value = storedDataHandler.get(key);
        if (value == null) throw new KeyNotFoundException("Key not found");
        return value;
    }

    @Override
    public void set(String key, String value) throws Exception {
        commitLogHandler.append(key, value);
        inMemoryCollection.set(key, value, storedDataHandler);
    }

    @Override
    public void delete(String key) throws Exception {

    }
}
