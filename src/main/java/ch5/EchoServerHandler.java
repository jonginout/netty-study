package ch5;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 수신된 데이터를 클라이언트 소켓 버퍼에 기록하고 버퍼의 데이터를 채널로 전송하는 비동기 메서드인
        // writeAndFlush를 호출하고 ChannelFuture 객체를 돌려받는다.
        ChannelFuture channelFuture = ctx.writeAndFlush(msg);
        // ChannelFuture 객체에 채널을 종료하는 리스너를 등록한다.
        // ChannelFutureListener.CLOSE 리스너는 네티가 제공하는 기본 리스너로서
        // ChannelFuture 객체가 완료 이벤트를 수신할 때 실행된다.
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
