package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

class EchoServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) { // (2)
        /**
         * https://javacoding.tistory.com/147
         * 네트뤄크 데이터의 기본 단위는 항상 바이
         * 자바 NIO는 ByteBuffer라는 자체 바이트 컨테이너를 제공하지만 이 클래스는 사용법이 너무 복잡해 사용하기 부담스럽다.
         * 네티에는 네트워크 개발자에게 더 나은 API를 제공하는 강력한 구현인 ByteBuf가 있다.
         */
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset()); // (3)
        System.out.println("수신한 문자열 [" + readMessage + "]"); //(4)
        context.write(msg); // (5)
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) { // (6)
        // channelRead 가 끝난 뒤 호출
        context.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
