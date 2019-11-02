package org.achyuth.filesystem;

public class FsUtilsFactory {

    public static FsUtils getWriter(String name) {
        if (name.equals("local")) {
            return new LocalFileSystemUtils();
        }
        // TODO: Other file system implementations

        else return null;
    }
}
