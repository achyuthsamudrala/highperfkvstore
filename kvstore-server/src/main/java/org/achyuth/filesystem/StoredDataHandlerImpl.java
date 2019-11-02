package org.achyuth.filesystem;

import java.util.Map;

public class StoredDataHandlerImpl implements StoredDataHandler {

    private final FsUtils fsUtils;

    public StoredDataHandlerImpl(FsUtils fsUtils) {
        this.fsUtils = fsUtils;
    }

    public String get(String key) {
        return null;
    }

    public void persist(Map<String, String> collection) {
    }
}
