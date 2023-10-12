package com.aston.demo.controller;

import com.aston.demo.dto.TransferMoneyDTO;
import com.aston.demo.model.Transaction;
import com.aston.demo.service.MoneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "Money",
        description = "Methods for running money"
)
@RestController("/money")
public class MoneyController {
    private MoneyService moneyService;

    @PatchMapping("/withdraw")
    @Operation(summary = "Make a withdraw")
    public Transaction withdraw(Long userId, Long accountId, Integer pin, Long amount){
        return moneyService.withdraw(userId, accountId, pin, amount);

    }

    @Operation(summary = "Transfer money to another user")
    @PatchMapping("/transfer")
    public Transaction transfer (TransferMoneyDTO dto){
        return moneyService.transfer(dto);
    }

    @Operation(summary = "Put money to account")
    @PatchMapping("/deposit")
    public Transaction deposit (Long userId,Long accountId,Integer pin, Integer amountToPut ){
        return moneyService.deposit(userId, accountId,pin, amountToPut);
    }

    @Autowired
    public void setMoneyService(MoneyService moneyService) {
        this.moneyService = moneyService;
    }
}
