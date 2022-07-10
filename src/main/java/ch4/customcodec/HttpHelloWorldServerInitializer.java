package ch4.customcodec;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class HttpHelloWorldServerInitializer extends ChannelInitializer<Channel> {

    private final SslContext sslCtx;

    public HttpHelloWorldServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        /**
         * 네티가 제공하는 코덱
         */
        p.addLast(new HttpServerCodec());
        /**
         * HttpServerCodec이 수신한 이벤트와 데이터를 처리하여 HTTP 객체로 변환한다음 channelRead 이벤트를
         * HttpHelloworldServerHandler 클래스로 전달한다.
         */
        p.addLast(new HttpHelloWorldServerHandler());
        /**
         * 순서대로 설정, 순서가 중요
         * HttpServerCodec 클래스는 인바운드와 아웃바운드 이벤트 핸들러를 모두 구현한다.
         */
    }
}
