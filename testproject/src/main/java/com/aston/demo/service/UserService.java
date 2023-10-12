package com.aston.demo.service;

import com.aston.demo.model.Transaction;
import com.aston.demo.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users getUserInfo(Long id);

    List<Transaction> getTransHistory(Long accountId);
}
