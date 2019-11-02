package org.achyuth.filesystem;

public interface FsUtils {
    void append(String fileName, String data) throws Exception;
    void write(String fileName, String data) throws Exception;
    void read(String fileName) throws Exception;
}
