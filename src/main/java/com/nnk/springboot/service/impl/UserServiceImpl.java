package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.inter.IUserService;
import com.nnk.springboot.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
    }

    @Override
    public User save(User user) {
        if (!PasswordUtils.isValid(user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters and contain uppercase, lowercase, number and special character.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
        if (!PasswordUtils.isValid(user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters and contain uppercase, lowercase, number and special character.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
        userRepository.delete(user);
    }
}
