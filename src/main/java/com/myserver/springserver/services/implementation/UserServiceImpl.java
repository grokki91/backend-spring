package com.myserver.springserver.services.implementation;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.model.MyUser;
import com.myserver.springserver.repository.UserRepo;
import com.myserver.springserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<MyUser> getAll() {
        return userRepo.findAll();
    }

    @Override
    public MyUser add(MyUser user) throws AlreadyExistException {
        Optional<MyUser> username = userRepo.findByUsername(user.getUsername());
        Optional<MyUser> email = userRepo.findByEmail(user.getEmail());

        if (username.isPresent()) {
            throw new AlreadyExistException(String.format("User with name '%s' is already exist", user.getUsername()));
        }

        if (email.isPresent()) {
            throw new AlreadyExistException(String.format("User with email '%s' is already exist", user.getEmail()));
        }

        return userRepo.save(user);
    }

    @Override
    public MyUser getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
       if (userRepo.findById(id).isEmpty()) {
           throw new UsernameNotFoundException("User not found");
       }
       userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }
}
