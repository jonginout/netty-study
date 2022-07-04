package ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // (1)
                .channel(NioSocketChannel.class) // (2)
                .handler(new ChannelInitializer<SocketChannel>() { // (3)
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new EchoClientHandler());
                    }
                });

            ChannelFuture future = bootstrap.connect("localhost", 8888).sync(); // (4)
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
