package handlers;

import memory.SSCollection;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;

public class BatchingRequestHandlerTest {

    @Test
    public void testSingleSetAndGet() {
        SSCollection ssCollection = new SSCollection();
        BatchingRequestHandler batchingRequestHandler = new BatchingRequestHandler(ssCollection);

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

    private static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    private static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
