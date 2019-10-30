package org.achyuth.handlers;

import org.achyuth.memory.SSCollection;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;

public class BatchingRequestHandlerTest {

    @Test
    public void testSingleSetAndGet() {
        SSCollection ssCollection = new SSCollection();
        BatchingRequestHandler batchingRequestHandler = new BatchingRequestHandler(ssCollection, 10000);

        String expectedKey = "somekey";
        double expectedValue = 2.34;

        byte[] key = expectedKey.getBytes();
        byte[] value = toByteArray(expectedValue);
        batchingRequestHandler.set(key, value);

        byte[] valueReceived = batchingRequestHandler.get(key);
        Assertions.assertEquals(value, valueReceived);

        double deserializedValue = toDouble(valueReceived);
        Assertions.assertEquals(expectedValue, deserializedValue);
    }

    @Test
    public void testCreationOfNewSegment() {
        int batchSize = 10;
        SSCollection ssCollection = new SSCollection();
        BatchingRequestHandler batchingRequestHandler = new BatchingRequestHandler(ssCollection, batchSize);

        String expectedKey = "basekey";
        double expectedValue = 2.34;
        byte[] value = toByteArray(expectedValue);

        Assertions.assertEquals(0, ssCollection.size());

        for (int i=0;i<batchSize;i++) {
            String transformedKey = expectedKey + i;
            byte[] key = transformedKey.getBytes();
            batchingRequestHandler.set(key, value);
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
