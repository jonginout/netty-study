package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext context) { // (1)
        String sendMessage = "Hello, Netty!";

        ByteBuf messageBuffer = Unpooled.buffer();
        messageBuffer.writeBytes(sendMessage.getBytes());

        StringBuilder builder = new StringBuilder();
        builder.append("전송한 문자열[");
        builder.append(sendMessage);
        builder.append("]");

        System.out.println(builder.toString());
        context.writeAndFlush(messageBuffer); // (2)
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) { // (3)
        String readMessage = ((ByteBuf)message).toString(Charset.defaultCharset()); // (4)

        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열[");
        builder.append(readMessage);
        builder.append("]");

        System.out.println(builder.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) { // (5)
        context.close(); // (6)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
