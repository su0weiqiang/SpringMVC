package com.Dao.Mapper.User;

import com.Dao.Entity.User.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User getUserByName(String username);
}
