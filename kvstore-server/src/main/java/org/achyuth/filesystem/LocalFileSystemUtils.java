package org.achyuth.filesystem;

import org.apache.log4j.Logger;

import java.io.*;

public class LocalFileSystemUtils implements FsUtils {

    private static final Logger LOGGER = Logger.getLogger(LocalFileSystemUtils.class);


    LocalFileSystemUtils() {
    }

    @Override
    public void append(String fileName, String data) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.append(data);
        }
	}

    @Override
    public void append(String fileName, byte[] data) throws Exception {
        try (FileOutputStream output = new FileOutputStream(fileName, true)) {
            output.write(data);
        }
    }

    @Override
    public void write(String fileName, byte[] data) throws Exception {

    }

    @Override
    public void read(String fileName) throws Exception {

    }
}
