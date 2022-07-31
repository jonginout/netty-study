package ch4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class EchoServerV4 {
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
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new EchoServerV4FirstHandler());
                            p.addLast(new EchoServerV4SecondHandler());
                        }
                    });

            // 부트스트랩 클래스의 bind 메서드는 포트 바인딩이 완료되기 전 까진 ChannelFuture를 리턴
            ChannelFuture bindFuture = b.bind(8888);
            // ChannelFuture 객체의 작업이 완료될 때 까지 (바인딩 할 때 까지) 블로킹하는 메서드
            bindFuture.sync();
            // bindFuture 객체를 통해서 채널을 얻어온다.
            // 여기서 얻어진 채널은 8888번 포트에 바인딩된 서버 채널이다.
            Channel serverChannel = bindFuture.channel();
            // 바인드가 완료된 서버 채널의 ChannelFuture 객체를 돌려준다.
            // 채널이 생성될 때 CloseFuture 객체도 같이 생성되므로
            // closeFuture 메서드 돌려주는 CloseFuture 객체는 항상 동일한 객체다
            ChannelFuture closeFuture = serverChannel.closeFuture();
            // CloseFuture 객체는 채널의 연결이 종료될 때 연결 종료 이벤트를 받는다.
            // 채널이 생성될 때 같이 생성되는 기본 CloseFuture 객체에는 아무 동작도 설정되어있지 않으므로
            // 이벤트를 받았을 때 아무 동작도 하지 않는다.
            closeFuture.sync();
            // 위 소스랑 똑같다.
            // ChannelFuture f = b.bind(8888).sync();
            // f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
