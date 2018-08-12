package pnunu.user.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import pnunu.thrift.user.UserService;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Author: pnunu
 * @Date: Created in 15:23 2018/8/8
 * @Description: thrift
 */
@Configuration
public class ThriftServer {

    @Value("${service.port}")
    private int servicePort;

    @Autowired
    private UserService.Iface userService;

    @PostConstruct
    public void startThriftServer() {

        TProcessor processor = new UserService.Processor<>(userService);
        TNonblockingServerSocket socket = null;
        try {
             socket = new TNonblockingServerSocket(servicePort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        args.processor(processor);
        //帧传输
        args.transportFactory(new TFramedTransport.Factory());
        //二进制
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);
        server.serve();
    }
}
