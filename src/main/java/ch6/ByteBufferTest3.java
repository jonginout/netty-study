package ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest3 {
    public static void main(String[] args) {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태 : " + firstBuffer);

        firstBuffer.put((byte) 10);

        System.out.println(firstBuffer);

        System.out.println(firstBuffer.get());

        System.out.println(firstBuffer);
    }
}
