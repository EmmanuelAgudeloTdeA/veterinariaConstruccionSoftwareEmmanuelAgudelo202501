package app.adapters.pets.entity;

import app.adapters.persons.entity.PersonEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "pets")
@Setter
@Getter
@NoArgsConstructor
public class PetEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long petId;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "owner_id")
    @ManyToOne()
    private PersonEntity owner;
    @Column(name = "age")
    private Date age;
    @Column(name = "breed")
    private String breed;
    @Column(name = "characteristics")
    private String characteristics;
    @Column(name = "weight")
    private float weight;
}
