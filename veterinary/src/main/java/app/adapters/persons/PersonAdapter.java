package app.adapters.persons;

import app.adapters.persons.entity.PersonEntity;
import app.adapters.persons.repository.PersonRepository;
import app.adapters.pets.entity.PetEntity;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.PersonPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
@NoArgsConstructor
public class PersonAdapter implements PersonPort {
    @Autowired
    PersonRepository personRepository;

    @Override
    public boolean existPerson(long document) {
        return personRepository.existsByDocument(document);
    }

    @Override
    public void savePerson(Person person) {
        PersonEntity personEntity = personEntityAdapter(person);
        personRepository.save(personEntity);
        person.setPersonId(personEntity.getPersonId());
    }

    @Override
    public Person findByDocument(long document) {
        PersonEntity personEntity = personRepository.findByDocument(document);
        return personAdapter(personEntity);
    }

    @Override
    public List<Pet> getPetsByPersonId(Long personId) {
        return List.of();
    }

    public Person personAdapter(PersonEntity personEntity) {
        if (personEntity == null) {
            return null;
        }
        Person person = new Person();
        person.setPersonId(personEntity.getPersonId());
        person.setDocument(personEntity.getDocument());
        person.setName(personEntity.getName());
        person.setDateOfBirthday(personEntity.getDateOfBirthday());
        person.setRole(personEntity.getRole());
        return person;
    }

    public PersonEntity personEntityAdapter(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setPersonId(person.getPersonId());
        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setDateOfBirthday(person.getDateOfBirthday());
        personEntity.setRole(person.getRole());
        return personEntity;
    }
}
