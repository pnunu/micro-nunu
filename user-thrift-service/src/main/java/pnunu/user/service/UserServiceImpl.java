package pnunu.user.service;

import org.apache.thrift.TException;
import pnunu.thrift.user.UserInfo;
import pnunu.thrift.user.UserService;

/**
 * @Author: pnunu
 * @Date: Created in 10:57 2018/8/6
 * @Description:
 */
public class UserServiceImpl implements UserService.Iface {
    @Override
    public UserInfo getUserById(int id) throws TException {
        return null;
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return null;
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {

    }
}
