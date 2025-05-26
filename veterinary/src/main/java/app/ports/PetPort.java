package app.ports;

import app.domain.models.Person;
import app.domain.models.Pet;

import java.util.List;

public interface PetPort {
    public void savePet(Pet pet);
    public List<Pet> getPetsByPerson(Person person);
}
