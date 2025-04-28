package com.raihan.realestate.service;

import com.raihan.realestate.model.User;
import com.raihan.realestate.repository.UserExistenceCheck;
import com.raihan.realestate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        List<User> userList = userRepository.findAll();
        return userList;
    }
    public boolean findByEmailAndPassword(String email, String password) {
       Optional<User> user = userRepository.findByEmailAndPassword(email,password);
       return user.isPresent();
    }
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get();
    }
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.get();
    }
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
    public User update(String username,User user) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User oldUser = userOptional.get();
            oldUser.setName(user.getName());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());
            oldUser.setUsername(user.getUsername());
            oldUser.setUserType(user.getUserType());
            oldUser.setPhone(user.getPhone());
            oldUser.setLocation(user.getLocation());
            userRepository.save(oldUser);
            return oldUser;
        }
        return null;
    }

    public long countUsers() {
        return userRepository.count();
    }
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
