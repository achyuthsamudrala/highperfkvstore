package org.achyuth.memory;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.ByteBuffer;

public class SSCollectionTest {

    @Test
    public void test() {
        double a = 2.4;

        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(a);
        byte[] ba = byteBuffer.array();

        String as = new String(ba);
        byte[] dec = as.getBytes();

        Assertions.assertEquals(2.4, ByteBuffer.wrap(dec).getDouble());
    }
}
