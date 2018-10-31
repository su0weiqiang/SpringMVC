package com.swq.Service.Common;

import com.swq.Dao.Entity.User.User;
import com.swq.Dao.Mapper.User.UserMapper;
import com.swq.Dao.Entity.User.User;
import com.swq.Dao.Mapper.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSystemServiceImpl implements UserSystemService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        User user=null;
        user=userMapper.getUserByName(username);
        return user;
    }
}
