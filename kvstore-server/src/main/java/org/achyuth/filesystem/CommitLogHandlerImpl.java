package org.achyuth.filesystem;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommitLogHandlerImpl implements CommitLogHandler {

    private static final String COMMIT_LOG_PREFIX = "commit-log-";
    private static final String COMMIT_LOG_SUFFIX = ".log";
    private final FsUtils fsUtils;

    public CommitLogHandlerImpl(FsUtils fsUtils) {
        this.fsUtils = fsUtils;
    }

    public void append(String key, String value) throws Exception {
        String currentFile = currentLogFile();
        byte[] data = createCommitLogEntry(key, value);
        fsUtils.append(currentFile, data);
    }

    private String currentLogFile() throws IOException {
        final LocalDateTime dateTime = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final String todayDate = dateTime.format(formatter);
        final String fileName = COMMIT_LOG_PREFIX + todayDate + COMMIT_LOG_SUFFIX;
        return fileName;
    }

    byte[] createCommitLogEntry(String key, String value) throws DecoderException {
        final byte[] keyBytes = Hex.decodeHex(key);
        final byte[] valueBytes = Hex.decodeHex(value);
        final byte[] keySize = intToByteArray(keyBytes.length);
        final byte[] valueSize = intToByteArray(valueBytes.length);
        return new byte[keySize.length + valueSize.length + keyBytes.length + valueBytes.length];
    }

    private int byteArrayToLeInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    byte[] intToByteArray(int i) {
        final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i);
        return bb.array();
    }
}
