namespace java thrift.generated
namespace py py.thrift.generated //支持Python的

//因为使用java比较习惯，所以定义别名成java的语法
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person{
    1:optional String username, //;号也是可以的
    2:optional int age,
    3:optional boolean married
}

exception DataException{
    1:optional String message,
    2:optional String callStack,
    3:optional String data //注意thrift中是不支持日期类型的
}

service PersonService{
    //定义客户端与服务端相互调用时的接口方法
    Person getPersonByUsername(1:required String username) throws(1:DataException dataException),
    void savePerson(1:required Person person) throws(1:DataException dataException)
}

