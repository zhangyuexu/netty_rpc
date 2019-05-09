package com.zyx.netty.firstcase;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    //初始化管道，一旦连接(即channel)被注册之后，这个方法就会被调，这个方法其实是一个回调方法
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道中可以有很多的channel handler
        ChannelPipeline channelPipeline = ch.pipeline();
        //HttpServerCodec它是把HttpRequestDecoder和HttpResponseEncoder合二为一了，对客户端的一个http请求先进行解码，然后做完处理后，对响应再编码返回。
        channelPipeline.addLast("httpServerCodec",new HttpServerCodec());//netty给提供的处理器
        channelPipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
