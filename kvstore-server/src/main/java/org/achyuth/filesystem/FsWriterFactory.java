package org.achyuth.filesystem;

public class FsWriterFactory {

    public static FsWriter getWriter(String name) {
        if (name.equals("local")) {
            return new LocalFileSystemWriter();
        }
        // TODO: Other file system implementations

        else return null;
    }
}
