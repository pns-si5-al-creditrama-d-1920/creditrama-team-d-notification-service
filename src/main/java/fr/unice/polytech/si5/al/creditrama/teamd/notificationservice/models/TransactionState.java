package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models;

public enum TransactionState {

    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    CANCEL("CANCEL");

    private String value;

    TransactionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
