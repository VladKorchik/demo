package com.aston.demo.repository;

import com.aston.demo.model.BankAccount;
import com.aston.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBankAccountReceiverIdOrBankAccountSenderId(BankAccount receiverAccountId, BankAccount senderAccountId );
}
