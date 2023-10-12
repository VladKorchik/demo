package com.aston.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Schema(description = "Transfer money data object")
@Getter
@AllArgsConstructor
public class TransferMoneyDTO {
    //sender
    private final Long accountIdFrom;
    private final Integer pin;
    private final Long amount;
    // receiver
    private final Long accountIdTo;

}
