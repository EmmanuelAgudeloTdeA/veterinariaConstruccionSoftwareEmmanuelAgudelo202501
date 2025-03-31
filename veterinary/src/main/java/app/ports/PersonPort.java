package app.ports;

import app.domain.models.Person;
import app.domain.models.Pet;

import java.util.List;

public interface PersonPort {
    public boolean existPerson(long document);
    public void savePerson(Person person);
    public Person findByDocument(long document);
    public List<Pet> getPetsByPersonId(Long personId);
}
