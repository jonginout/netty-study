package ch6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class CreateByteBufferTest {

    @Test
    public void createTest() {
        // 11개의 char형 데이터를 저장할 수 있는 힙 버퍼를 생성한다.
        CharBuffer heapBuffer = CharBuffer.allocate(11);
        Assertions.assertEquals(11, heapBuffer.capacity());
        Assertions.assertEquals(false, heapBuffer.isDirect());

        // 11개의 byte형 데이터를 저장할 수 있는 다이렉트 버퍼를 생성한다.
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(11);
        Assertions.assertEquals(11, directBuffer.capacity());
        Assertions.assertEquals(true, directBuffer.isDirect());

        // 11개의 데이터가 저장된 int형 배열을 생성한다.
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0};
        // 위에서 생성한 array 배열을 감싸는 int 형 버퍼를 생성한다.
        // 이때 생성된 버퍼는 JVM의 힙 영역에서 생성된다.
        IntBuffer intHeapBuffer = IntBuffer.wrap(array);
        Assertions.assertEquals(11, intHeapBuffer.capacity());
        Assertions.assertEquals(false, intHeapBuffer.isDirect());
    }
}
