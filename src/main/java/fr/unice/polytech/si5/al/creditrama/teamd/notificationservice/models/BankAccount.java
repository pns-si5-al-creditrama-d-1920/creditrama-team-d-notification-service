package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BankAccount {
    private Double balance;

    private String iban;

    private long client;

    private String accountNumber;

    public void addMoney(double amount) {
        this.balance += amount;
    }

    public void removeMoney(double amount) {
        this.balance -= amount;
    }
}
