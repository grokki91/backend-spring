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

    private static final String USER_NOT_FOUND = "User with ID=%s does not exist";
    private static final String USER_FOUND = "User with %s=%s already exists";

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
            throw new AlreadyExistException(String.format(USER_FOUND, "USERNAME", user.getUsername()));
        }

        if (email.isPresent()) {
            throw new AlreadyExistException(String.format(USER_FOUND, "EMAIL", user.getEmail()));
        }

        return userRepo.save(user);
    }

    @Override
    public MyUser getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    @Override
    public void deleteUser(Long id) {
       if (userRepo.findById(id).isEmpty()) {
           throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, id));
       }
       userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }
}
