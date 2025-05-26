package app.adapters.rest.request;

import app.domain.models.Person;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PetRequest {
    private String name;
    private Person owner;
    private String age;
    private String breed;
    private String characteristics;
    private float weight;
}
