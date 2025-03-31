package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class Invoice {
    private long invoiceId;
    private Pet pet;
    private Person owner;
    private Order order;
    private String productName;
    private float price;
    private int amount;
    private Timestamp date;

    public Invoice(long invoiceId, Pet pet, Person owner, Order order, String productName, float price, int amount, Timestamp date) {
        this.invoiceId = invoiceId;
        this.pet = pet;
        this.owner = owner;
        this.order = order;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }
}
