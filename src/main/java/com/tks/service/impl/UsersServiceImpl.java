package com.tks.service.impl;

import com.tks.models.Address;
import com.tks.models.Users;
import com.tks.repository.UsersRepository;
import com.tks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;


    @Override
    public List<Users> addDummyProductOnStartup() {
        return usersRepository.saveAll(getDummyUsers());
    }

    @Override
    public Users saveUser(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Optional<Users> findUserByNameAndPhone(String name, String mobile) {
        return usersRepository.findByCustomerNameAndMobile(name, mobile);
    }

    private List<Users> getDummyUsers() {
        Set<Address> addresses = new HashSet<>();
        addresses.add(new Address("Test1", "Test2", "686671"));
        Users users = new Users("John", "9447599402", addresses);
        return Arrays.asList(users);
    }


}
