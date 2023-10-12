package com.aston.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", referencedColumnName = "id")
    @JsonIgnore
    private Users users;
    @JsonIgnore
    private Integer pin;
    @OneToMany
    @JoinColumn(name = "Transaction")
    private Set<Transaction> transactions;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
