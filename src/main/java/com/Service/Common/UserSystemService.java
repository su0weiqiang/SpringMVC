package com.Service.Common;

import com.Dao.Entity.User.User;

public interface UserSystemService {
    public User getUserByUserName(String username);
}
