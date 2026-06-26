package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.inter.IUserService;
import com.nnk.springboot.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        log.debug("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        log.debug("Fetching user by id={}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for id={}", id);
                    return new IllegalArgumentException("Invalid User Id: " + id);
                });
    }

    @Override
    public User save(User user) {
        if (!PasswordUtils.isValid(user.getPassword())) {
            log.warn("Save rejected: invalid password for user '{}'", user.getUsername());
            throw new IllegalArgumentException("Password must be at least 8 characters and contain uppercase, lowercase, number and special character.");
        }
        log.info("Saving new user '{}'", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid User Id: " + id);
                });
        if (!PasswordUtils.isValid(user.getPassword())) {
            log.warn("Update rejected: invalid password for user '{}'", user.getUsername());
            throw new IllegalArgumentException("Password must be at least 8 characters and contain uppercase, lowercase, number and special character.");
        }
        log.info("Updating user id={}", id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid User Id: " + id);
                });
        log.info("Deleting user id={}", id);
        userRepository.delete(user);
    }
}
