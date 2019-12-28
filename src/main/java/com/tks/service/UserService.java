package com.tks.service;

import com.tks.models.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Users> addDummyProductOnStartup();

    Users saveUser(Users users);

    Optional<Users> findUserByNameAndPhone(String name, String mobile);
}
