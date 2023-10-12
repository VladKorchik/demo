package com.aston.demo.service;

import com.aston.demo.exception.UserNotFoundException;
import com.aston.demo.model.BankAccount;
import com.aston.demo.model.Transaction;
import com.aston.demo.model.Users;
import com.aston.demo.repository.AccountRepository;
import com.aston.demo.repository.TransRepository;
import com.aston.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceIml implements UserService {
    private TransRepository transRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    @Override
    public Users getUserInfo(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<Transaction> getTransHistory(Long accountId) {
        Optional<BankAccount> account = accountRepository.findById(Long.valueOf(accountId));
        if (account.isPresent()) {
            return transRepository.findByBankAccountReceiverIdOrBankAccountSenderId(account.get(), account.get());
        }
        throw new UserNotFoundException("Wrong user or accountID");
    }

    @Autowired
    public void setTransRepository(TransRepository transRepository) {
        this.transRepository = transRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
