package org.achyuth.handlers;

import org.achyuth.memory.SSCollection;

public interface RequestHandler {

    String get(String key) throws Exception;
    void set(String key, String value) throws Exception;
    SSCollection getSnapshot();
}
