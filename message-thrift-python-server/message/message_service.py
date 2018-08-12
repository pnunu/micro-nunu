# coding: utf-8
import smtplib
from email.header import Header
from email.mime.text import MIMEText

from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
from thrift.transport import TSocket
from thrift.transport import TTransport

from message.api import MessageService

sender = 'bjnunu@163.com'
authCode = 'bjnunu163'
smtp163Server = 'smtp.163.com'
class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage, mobile: " + mobile + ", message: " + message)
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage, email: " + email + ", message: " + message)
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = server
        messageObj['To'] = email
        messageObj['Subject'] = Header('用户注册-小小暮雨', 'utf-8')
        try:
            smtpObj = smtplib.SMTP(smtp163Server)
            smtpObj.login(sender, authCode)
            smtpObj.send(sender, [email], messageObj.as_string())
            print("send mail success")
            return True
        except smtplib.SMTPException as ex:
            print("send mail failed！")
            print(ex)
            return False



if __name__ == '__main__':
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    transport = TSocket.TServerSocket("127.0.0.1", "9090")
    tfactory = TTransport.TFramedTransportFactory
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print("python thrift server start")
    server.serve()
    print("python thrift server stop")
    # print(TSocket)