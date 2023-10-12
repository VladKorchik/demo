package com.aston.demo.service;

import com.aston.demo.dto.TransferMoneyDTO;
import com.aston.demo.exception.NotEnoughMoney;
import com.aston.demo.exception.UserNotFoundException;
import com.aston.demo.exception.WrongPinException;
import com.aston.demo.model.BankAccount;
import com.aston.demo.model.Transaction;
import com.aston.demo.model.enums.TransResult;
import com.aston.demo.model.enums.TransType;
import com.aston.demo.repository.AccountRepository;
import com.aston.demo.repository.TransRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class MoneyServiceImpl implements MoneyService {
    private TransRepository transRepository;
    private AccountRepository accountRepository;

    @Transactional
    @Override
    public Transaction withdraw(Long userId, Long accountId, Integer pin, Long amount) {
        Optional<BankAccount> account = accountRepository.findById(accountId);
        BankAccount bankAccount = null;
        Transaction transaction;
        try {
            bankAccount = checkUser(pin, account);
            checkSum(amount, bankAccount);
            bankAccount.setAmount(BigDecimal.valueOf(bankAccount.getAmount().longValue() - amount));
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(amount))
                    .bankAccountReceiverId(bankAccount)
                    .transType(TransType.WITHDRAW)
                    .transDate(new Date())
                    .transResult(TransResult.SUCCEED)
                    .build();
            accountRepository.save(bankAccount);
            transRepository.save(transaction);
            return transaction;
        } catch (Exception ex) {
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(amount))
                    .bankAccountSenderId(bankAccount)
                    .transType(TransType.WITHDRAW)
                    .transDate(new Date())
                    .transResult(TransResult.FAILED)
                    .description(ex.getMessage())
                    .build();
            transRepository.save(transaction);
            return transaction;
        }
    }

    @Transactional
    @Override
    public Transaction transfer(TransferMoneyDTO dto) {
        Optional<BankAccount> accountFrom = accountRepository.findById(dto.getAccountIdFrom());
        Optional<BankAccount> accountTo = accountRepository.findById(dto.getAccountIdTo());
        BankAccount bankAccountFrom = null;
        BankAccount bankAccountTo = null;
        Transaction transaction = null;
        try {
            if (accountTo.isEmpty()) {
                throw new UserNotFoundException("Receiver not found");
            }
            bankAccountTo = accountTo.get();
            bankAccountFrom = checkUser(dto.getPin(), accountFrom);
            checkSum(dto.getAmount().longValue(), bankAccountFrom);
            bankAccountFrom.setAmount(BigDecimal.valueOf(bankAccountFrom.getAmount().longValue() - dto.getAmount()));
            bankAccountTo.setAmount(BigDecimal.valueOf(bankAccountFrom.getAmount().longValue() + dto.getAmount()));
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(dto.getAmount()))
                    .bankAccountSenderId(bankAccountFrom)
                    .bankAccountReceiverId(bankAccountTo)
                    .transDate(new Date())
                    .transType(TransType.TRANSFER)
                    .transResult(TransResult.SUCCEED)
                    .build();
            accountRepository.save(bankAccountTo);
            accountRepository.save(bankAccountFrom);
            transRepository.save(transaction);
        } catch (Exception e) {
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(dto.getAmount()))
                    .bankAccountSenderId(bankAccountFrom)
                    .bankAccountReceiverId(bankAccountTo)
                    .transDate(new Date())
                    .transType(TransType.TRANSFER)
                    .transResult(TransResult.FAILED)
                    .description(e.getMessage())
                    .build();
            transRepository.save(transaction);
        }
        return transaction;
    }

    @Transactional
    @Override
    public Transaction deposit(Long userId, Long accountId, Integer pin, Integer amount) {
        Optional<BankAccount> account = accountRepository.findById(accountId);
        BankAccount bankAccount = null;
        Transaction transaction;
        try {
            bankAccount = checkUser(pin, account);
            bankAccount.setAmount(BigDecimal.valueOf(bankAccount.getAmount().longValue() + amount));
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(amount))
                    .bankAccountReceiverId(bankAccount)
                    .transDate(new Date())
                    .transType(TransType.DEPOSIT)
                    .transResult(TransResult.SUCCEED)
                    .build();
            accountRepository.save(bankAccount);
            transRepository.save(transaction);
        } catch (Exception ex) {
            transaction = Transaction.builder()
                    .amount(BigDecimal.valueOf(amount))
                    .bankAccountReceiverId(bankAccount)
                    .transType(TransType.DEPOSIT)
                    .transDate(new Date())
                    .transResult(TransResult.FAILED)
                    .description(ex.getMessage())
                    .build();
            transRepository.save(transaction);
        }
        return transaction;
    }


    @Autowired
    public void setTransRepository(TransRepository transRepository) {
        this.transRepository = transRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private BankAccount checkUser(Integer pin, Optional<BankAccount> account) {
        BankAccount bankAccount;
        if (account.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        bankAccount = account.get();
        if (!bankAccount.getPin().equals(pin)) {
            throw new WrongPinException("Wrong pin");
        }
        return bankAccount;
    }

    private static void checkSum(Long amount, BankAccount bankAccount) {
        if (amount > bankAccount.getAmount().longValue()) {
            throw new NotEnoughMoney("Not enough money");
        }
    }
}
