package app.adapters.orders.entity;

import app.adapters.persons.entity.PersonEntity;
import app.adapters.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderId;
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private PersonEntity owner;
    @JoinColumn(name = "veterinarian_id")
    @ManyToOne
    private UserEntity veterinarian;
    @Column(name = "medication_name")
    private String medicationName;
    @Column(name = "medication_dosage")
    private String medicationDosage;
    @Column(name = "date")
    private Timestamp date;
}
