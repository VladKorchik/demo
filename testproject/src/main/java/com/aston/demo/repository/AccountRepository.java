package com.aston.demo.repository;


import com.aston.demo.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {

    public Optional<BankAccount> findById(Long id);
}
