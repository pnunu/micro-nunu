namespace java com.imooc.thfift.message
namespace py message.api

service MessageseService {
    bool sendMobileMessage(1:string mobile, 2:string message);
    bool sendEmailMessage(1:string email, 2:string message);
}