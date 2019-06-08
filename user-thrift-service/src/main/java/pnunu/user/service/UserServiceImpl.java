package pnunu.user.service;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pnunu.thrift.user.UserInfo;
import pnunu.thrift.user.UserService;
import pnunu.user.mapper.UserMapper;

/**
 * @Author: pnunu
 * @Date: Created in 10:57 2018/8/6
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService.Iface {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return userMapper.getTeacherById(id);
    }
}
