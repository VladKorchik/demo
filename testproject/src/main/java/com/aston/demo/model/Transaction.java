package com.aston.demo.model;

import com.aston.demo.model.enums.TransResult;
import com.aston.demo.model.enums.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Transaction model")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransType transType;
    private BigDecimal amount;
    private Date transDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountSenderId", referencedColumnName = "id")
    private BankAccount bankAccountSenderId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountReceiverId", referencedColumnName = "id")
    private BankAccount bankAccountReceiverId;
    @Enumerated(EnumType.STRING)
    private TransResult transResult;
    private String description;
}
