package com.aston.demo.service;

import com.aston.demo.dto.TransferMoneyDTO;
import com.aston.demo.model.Transaction;

public interface MoneyService {
    Transaction withdraw(Long userId, Long accountId, Integer pin, Long amount);

    Transaction transfer(TransferMoneyDTO dto);

    Transaction deposit(Long userId, Long accountId,Integer pin, Integer amountToPut);
}
