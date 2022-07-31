package ch6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class ReadByteBufferTest {

    @Test
    public void test() {
        byte[] tempArray = {1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
        ByteBuffer firstBuffer = ByteBuffer.wrap(tempArray);

        Assertions.assertEquals(0, firstBuffer.position());
        Assertions.assertEquals(11, firstBuffer.limit());

        Assertions.assertEquals(1, firstBuffer.get());
        Assertions.assertEquals(2, firstBuffer.get());
        Assertions.assertEquals(3, firstBuffer.get());
        Assertions.assertEquals(4, firstBuffer.get());
        Assertions.assertEquals(4, firstBuffer.position());
        Assertions.assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();

        Assertions.assertEquals(0, firstBuffer.position());
        Assertions.assertEquals(4, firstBuffer.limit());

        firstBuffer.get(3);

        Assertions.assertEquals(0, firstBuffer.position());
    }
}
