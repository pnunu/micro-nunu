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
title = '用户注册-小小暮雨'  # 邮件主题
class MessageServiceHandler:

    def sendMobileMessage(self, mobile, content):
        print("sendMobileMessage, mobile: " + mobile + ", content: " + content)
        return True

    def sendEmailMessage(self, email, content):
        print("sendEmailMessage, email: " + email + ", content: " + content)
        message = MIMEText(content, 'plain', 'utf-8')  # 内容, 格式, 编码
        message['From'] = "{}".format(sender)
        message['To'] = email
        message['Subject'] = title

        try:
            smtpObj = smtplib.SMTP_SSL(smtp163Server, 465)  # 启用SSL发信, 端口一般是465
            smtpObj.login(sender, authCode)  # 登录验证
            smtpObj.sendmail(sender, email, message.as_string())  # 发送
            print("mail has been send successfully.")
            return True
        except smtplib.SMTPException as e:
            print(e)
            return False

        # messageObj = MIMEText(message, "plain")
        # messageObj['From'] = server
        # messageObj['To'] = email
        # messageObj['Subject'] = Header('用户注册-小小暮雨')
        # try:
        #     smtpObj = smtplib.SMTP(smtp163Server)
        #     smtpObj.login(sender, authCode)
        #     # smtpObj.send(sender, [email], messageObj.as_string())
        #     smtpObj.send_message(sender, [email], messageObj.as_string())
        #     print("send mail success")
        #     return True
        # except smtplib.SMTPException as ex:
        #     print("send mail failed！")
        #     print(ex)
        #     return False



if __name__ == '__main__':
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    transport = TSocket.TServerSocket("127.0.0.1", "9090")
    tfactory = TTransport.TFramedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print ("python thrift server start")
    server.serve()
    print ("python thrift server exit")


# if __name__ == '__main__':
#     handler = MessageServiceHandler()
#     processor = MessageService.Processor(handler)
#     transport = TSocket.TServerSocket("127.0.0.1", "9090")
#     tfactory = TTransport.TFramedTransportFactory()
#     pfactory = TBinaryProtocol.TBinaryProtocolFactory()
#     server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
#     print("python thrift server start")
#     server.serve()
#     print("python thrift server stop")
#     # print(TSocket)
