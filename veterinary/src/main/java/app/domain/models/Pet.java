package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class Pet {
    private long petId;
    private String name;
    private Person owner;
    private Date age;
    private String breed;
    private String characteristics;
    private float weight;

    public Pet(long petId, String name, Person owner, Date age, String breed, String characteristics, float weight) {
        this.petId = petId;
        this.name = name;
        this.owner = owner;
        this.age = age;
        this.breed = breed;
        this.characteristics = characteristics;
        this.weight = weight;
    }
}
