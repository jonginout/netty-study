package ch6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class WriteByteBufferTest {

    @Test
    public void test() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        Assertions.assertEquals(0, firstBuffer.position());
        Assertions.assertEquals(11, firstBuffer.limit());

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        firstBuffer.put((byte) 3);
        firstBuffer.put((byte) 4);

        Assertions.assertEquals(4, firstBuffer.position());
        Assertions.assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();

        Assertions.assertEquals(0, firstBuffer.position());
        Assertions.assertEquals(4, firstBuffer.limit());
    }
}
