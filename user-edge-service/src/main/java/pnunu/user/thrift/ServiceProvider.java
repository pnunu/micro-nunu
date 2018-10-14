package pnunu.user.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pnunu.thrift.message.MessageService;
import pnunu.thrift.user.UserService;

/**
 * @Author: pnunu
 * @Date: Created in 16:29 2018/8/8
 * @Description: thrift
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String userServerIp;
    @Value("${thrift.user.port}")
    private int userServerPort;

    @Value("${thrift.message.ip}")
    private String messageServerIp;
    @Value("${thrift.message.port}")
    private int messageServerPort;

    private enum ServiceType {
        USER,
        MESSAGE
    }

    public UserService.Client getUserService() {
        return getServiceClient(userServerIp, userServerPort, ServiceType.USER);
    }

    public MessageService.Client getMessageService() {
        return getServiceClient(messageServerIp, messageServerPort, ServiceType.MESSAGE);
    }

    private <T> T getServiceClient(String ip, int port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip, port, 30000);
        // 帧传输
        TTransport tTransport = new TFramedTransport(socket);
        try {
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        // 二进制协议
        TProtocol protocol = new TBinaryProtocol(tTransport);

        TServiceClient client = null;
        switch (serviceType) {
            case USER:
                client = new UserService.Client(protocol);
                break;
            case MESSAGE:
                client = new MessageService.Client(protocol);
                break;
        }
        return (T) client;
    }
}
