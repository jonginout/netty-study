package ch4.customcodec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

/**
 * HttpServerCodec으로 부터 수신된 HTTP 객체를 처리하는 channelRead 이벤트를 처리해야하므로
 * ChannelInboundHandlerAdapter 추상 클래스를 상속받는다.
 */
public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 웹 브라우저로 전송할 Hello World 메시지 상수 선언
     */
    private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Type");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * 웹 브라우저로 데이터가 모두 수신되었을 때 채널 버퍼의 내용을 웹 브라우저로 전송한다.
         */
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 수신된 객체가 HttpRequest 일때 HttpResponse 객체를 생성하고 헤더와 Hello World 메시지를 저장
         */
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (HttpHeaders.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
            }

            boolean keepAlive = HttpHeaders.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(CONTENT));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(req);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
