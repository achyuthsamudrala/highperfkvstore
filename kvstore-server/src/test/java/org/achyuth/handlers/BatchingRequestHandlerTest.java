package org.achyuth.handlers;

import org.achyuth.memory.SSCollection;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;

public class BatchingRequestHandlerTest {

    @Test
    public void testSingleSetAndGet() throws Exception {
        SSCollection ssCollection = new SSCollection();
        BatchingRequestHandler batchingRequestHandler = new BatchingRequestHandler(ssCollection, 10000);

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

    @Test
    public void testCreationOfNewSegment() throws Exception {
        int batchSize = 10;
        SSCollection ssCollection = new SSCollection();
        BatchingRequestHandler batchingRequestHandler = new BatchingRequestHandler(ssCollection, batchSize);

        String expectedKey = "basekey";
        double expectedValue = 2.34;

        Assertions.assertEquals(0, ssCollection.size());

        for (int i=0;i<batchSize;i++) {
            String transformedKey = expectedKey + i;
            String hexRepKey = Hex.encodeHexString(transformedKey.getBytes());
            String hexRepvalue = Hex.encodeHexString(toByteArray(expectedValue));
            batchingRequestHandler.set(hexRepKey, hexRepvalue);
            Assertions.assertEquals((i+1)%10, batchingRequestHandler.getCurrentActiveBatch().size());
        }
        Assertions.assertEquals(1, ssCollection.size());
        Assertions.assertEquals(0, batchingRequestHandler.getCurrentActiveBatch().size());
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
