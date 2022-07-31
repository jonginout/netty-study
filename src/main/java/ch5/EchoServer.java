package ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    /**
                     *  SocketChannel(즉, 연결된 클라이언트 소켓 채널)에 설정된 채널 파이프라인을 가져온다
                     *  (네티의 내부에서는 클라이언트 소켓 채널을 생성할 때 빈 채널 파이프라인 객체를 생성하여 할당한다.)
                     */
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new EchoServerHandler());
                }
            });

            ChannelFuture f = b.bind(8888).sync();
            f.channel().closeFuture().sync();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
