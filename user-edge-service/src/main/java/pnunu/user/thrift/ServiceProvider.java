package pnunu.user.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pnunu.thrift.user.UserService;

/**
 * @Author: pnunu
 * @Date: Created in 16:29 2018/8/8
 * @Description: thrift
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;
    @Value("${thrift.user.port}")
    private int serverPort;

    public UserService.Client getUserService() {
        TSocket socket = new TSocket(serverIp, serverPort, 3000);
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
        UserService.Client client = new UserService.Client(protocol);
        return client;
    }
}
