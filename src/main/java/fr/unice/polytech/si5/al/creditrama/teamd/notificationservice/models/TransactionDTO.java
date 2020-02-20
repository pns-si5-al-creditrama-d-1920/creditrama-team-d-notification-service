package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TransactionDTO {
    private String uuid;

    private BankAccount source;

    private BankAccount dest;

    private double amount;

    private LocalDateTime createdTransaction;

    private TransactionState transactionState;

    private short code;
}
