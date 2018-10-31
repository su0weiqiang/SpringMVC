package com.swq.Dao.Mapper.User;

import com.swq.Dao.Entity.User.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User getUserByName(String username);
}
