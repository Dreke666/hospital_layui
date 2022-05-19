package com.zhang.hospital.dao;


import com.zhang.hospital.entity.User;
import com.zhang.hospital.entity.UserSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    void insUser(User user);

    User login(@Param("username")String username, @Param("password")String password);

    User getUserById(Integer user_id);

    void updateUser(User user);

    User getUserByUserName(String username);

    List<User> getAllUserList(UserSearch search);

    void updateUserStatusById(@Param("user_id")int user_id, @Param("status")int status);

    void deleteUserById(int user_id);


}
