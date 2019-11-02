package org.achyuth.filesystem;

public interface CommitLogHandler {
    boolean append(String key, String value);
}
