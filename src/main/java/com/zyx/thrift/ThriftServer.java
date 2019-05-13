package com.zyx.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;

import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;

public class ThriftServer {
    public static void main(String[] args) throws Exception{
        //thrift支持的服务器socket对象类型很多，我这里使用其中的一种，非阻塞的ServerSocket
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        //半同步半异步的server
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        //IDL中定义的接口处理器，里面的泛型是真正的实现类
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        //设定几个工厂，协议工厂，传输工厂，处理器工厂
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        //启动server
        TServer server = new THsHaServer(arg);
        System.out.println("Server started!");
        server.serve();



    }

}
