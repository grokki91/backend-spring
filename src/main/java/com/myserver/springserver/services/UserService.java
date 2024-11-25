package com.myserver.springserver.services;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.model.MyUser;

import java.util.List;

public interface UserService {
    List<MyUser> getAll();

    MyUser save(MyUser user) throws AlreadyExistException;

    MyUser updateUser(Long id, MyUser user) throws AlreadyExistException;

    MyUser getUser(Long id);

    void deleteUser(Long id);

    void deleteAllUsers();

    void changePassword(Long id, String currentPassword, String newPassword, String confirmPassword) throws Exception;
}
