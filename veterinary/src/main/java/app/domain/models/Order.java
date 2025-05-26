package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    private long orderId;
    private Person owner;
    private Person veterinarian;
    private String medicationName;
    private String medicationDosage;
    private Timestamp date;

    public Order(long orderId, Person owner, Person veterinarian, String medicationName, String medicationDosage, Timestamp date) {
        this.orderId = orderId;
        this.owner = owner;
        this.veterinarian = veterinarian;
        this.medicationName = medicationName;
        this.medicationDosage = medicationDosage;
        this.date = date;
    }
}
