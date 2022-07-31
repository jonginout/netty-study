package ch6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class RightByteBufferTest3 {

    @Test
    public void test() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태 : " + firstBuffer);

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);

        Assertions.assertEquals(2, firstBuffer.position());

        firstBuffer.rewind();
        Assertions.assertEquals(0, firstBuffer.position());

        Assertions.assertEquals(1, firstBuffer.get());
        Assertions.assertEquals(1, firstBuffer.position());

        System.out.println(firstBuffer);
    }
}
