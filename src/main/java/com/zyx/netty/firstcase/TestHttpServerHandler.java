package com.zyx.netty.firstcase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import java.net.URI;


public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * <strong>Please keep in mind that this method will be renamed to
     * {@code messageReceived(ChannelHandlerContext, I)} in 5.0.</strong>
     *
     * Is called for each message of type {@link}.
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception    is thrown if an error occurred
     */
    //这个方法是读取客户端发过来的请求并向客户端返回响应的方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());
        Thread.sleep(9000);//目的是看下有哪几个handler注册到管道了，用lsof -i:8899 看下连接

        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求的方法名："+httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                return;
            }

            System.out.println("真正的请求开始执行");
            //构造响应（返回）内容
            ByteBuf content = Unpooled.copiedBuffer("Hello World ", CharsetUtil.UTF_8);

            //netty所提供的专门支撑响应的对象FullHttpResponse response
            //HTTP1.1协议会有一个keep-alive长连接协议，比如3分钟，如果3分钟客户端不再发送新的请求，服务器端就会主动把连接关闭掉
            //HTTP1.0协议就没有一个keep-alive，它是一个短连接协议，请求发过来后服务器端就会主动把连接关闭掉
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            //设置响应头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //返回客户端
            ctx.writeAndFlush(response);

            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
