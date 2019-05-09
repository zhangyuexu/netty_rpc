package com.zyx.protobuf;

public class ProtoBufTest {
    public static void main(String[] args) throws Exception{
        //1、先把对象构造好，构造好之后把它生成到字节数组里面
        //使用protobuf构建对象时一定要使用这种方式，因为message是只读的，只有builder是可读写的
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("张三").
                setAge(20).
                setAddress("北京").
                build();//newBuilder是对对象生成一个构建器对象，返回的是一个builder对象

        byte[] student2ByteArray = student.toByteArray();


        //2、从字节数组里面把它读取出来，还原成一个对象，再把对象内容输出出来
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
