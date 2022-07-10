package ch3;

import ch1.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class EchoServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        /**
         * 스레드 수를 명확하게 정하지 않으면 하드웨어 코어 수를 기준으로 결정
         * 스레드 수는 하드웨어가 가지고 있는 CPU 코어 수의 2배를 사용한다.
         * 만약 서버 어플리케이션이 동작하는 하드웨어가 4코어 CPU이고 하이퍼 스레딩을 지원한다면 스레드 16개가 생성된다.
         * (4코어 * 2(하이퍼 스레딩) * 2배수 = 16
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            /**
             * ServerBootstrap 객체에 서버 어플리케이션이 사용할 두 스레드 그룹을 설정함
             * 첫 번째 쓰레드 그룹은 클라이언트의 연결을 수락하는 부모 스레드 그룹
             * 두번째 쓰레드 그룹은 연결된 클라이언트의 소켓으로부터 데이터 입출력 및 이벤트 처리르 담당하느 자식 스레드 그룹
             */
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            p.addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(8888).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
