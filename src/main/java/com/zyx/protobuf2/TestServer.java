package com.zyx.protobuf2;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestServer {
    public static void main(String[] args){
        //先定义两个死循环的线程组(事件循环组)，类似于tomcat，一直接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();//只负责接收客户端的连接，不对连接做任何的处理，它把连接交给worker线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();//负责对连接进行处理
        try {
            //服务端的编写
            /**
             * {@link Bootstrap} sub-class which allows easy bootstrap of {@link ServerChannel}
             *
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();//服务端启动的类
            //管道，子处理器
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.INFO)).
                    childHandler(new TestServerInitializer());//这个实例的创建是通过反射的机制创建的

            //监控端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();

            //关闭端口监听
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
