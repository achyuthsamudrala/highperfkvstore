package org.achyuth.filesystem;

import java.util.Map;

public interface StoredDataHandler {

    String get(String key);
    void persist(Map<String, String> collection);
}
