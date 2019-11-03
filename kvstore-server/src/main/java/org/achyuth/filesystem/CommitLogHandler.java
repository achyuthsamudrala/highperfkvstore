package org.achyuth.filesystem;

public interface CommitLogHandler {
    void append(String key, String value) throws Exception;
}
