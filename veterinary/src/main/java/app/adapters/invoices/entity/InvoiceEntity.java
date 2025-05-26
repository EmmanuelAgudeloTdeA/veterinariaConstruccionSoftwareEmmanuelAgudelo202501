package app.adapters.invoices.entity;

import app.adapters.orders.entity.OrderEntity;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long invoiceId;
    @JoinColumn(name = "pet_id")
    @ManyToOne
    private PetEntity pet;
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private PersonEntity owner;
    @JoinColumn(name = "order_id")
    @ManyToOne
    private OrderEntity order;
    @Column(name = "product_name")
    private String productName;
    private float price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "date")
    private Timestamp date;
}
