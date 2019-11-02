package org.achyuth.handlers;

import org.achyuth.filesystem.CommitLogHandler;
import org.achyuth.filesystem.StoredDataHandlerImpl;
import org.achyuth.memory.MemoryCollection;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;

public class BatchingRequestHandlerTest {

    @Test
    public void testSingleSetAndGet() throws Exception {
        MemoryCollection ssCollection = new MemoryCollection(10000);
        RequestHandlerImpl batchingRequestHandler = new RequestHandlerImpl(ssCollection,
                                                    new MockCommitLogHandlerImpl(), new StoredDataHandlerImpl(null));

        String expectedKey = "somekey";
        double expectedValue = 2.34;

        byte[] key = expectedKey.getBytes();
        String hexRepKey = Hex.encodeHexString(key);
        byte[] value = toByteArray(expectedValue);
        String hexRepValue = Hex.encodeHexString(value);

        batchingRequestHandler.set(hexRepKey, hexRepValue);

        String valueReceived = batchingRequestHandler.get(hexRepKey);
        Assertions.assertEquals(hexRepValue, valueReceived);

    }

    private class MockCommitLogHandlerImpl implements CommitLogHandler {
        @Override
        public boolean append(String key, String value) {
            return true;
        }
    }

    private static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    private static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
