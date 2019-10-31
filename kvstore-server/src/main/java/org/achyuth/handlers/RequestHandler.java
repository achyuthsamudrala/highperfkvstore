package org.achyuth.handlers;

import org.achyuth.exceptions.KeyNotFoundException;
import org.achyuth.memory.SSCollection;

public interface RequestHandler {

    String get(String key) throws KeyNotFoundException;
    void set(String key, String value) throws Exception;
    SSCollection getSnapshot();
}
