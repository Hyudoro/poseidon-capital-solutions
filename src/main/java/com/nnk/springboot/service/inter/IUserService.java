package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(Integer id);

    User save(User user);

    User update(Integer id, User user);

    void delete(Integer id);
}
