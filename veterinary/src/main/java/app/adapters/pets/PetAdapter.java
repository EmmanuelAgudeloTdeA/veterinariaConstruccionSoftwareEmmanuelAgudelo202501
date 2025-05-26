package app.adapters.pets;

import app.adapters.persons.PersonAdapter;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import app.adapters.pets.repository.PetRepository;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.PetPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Service
public class PetAdapter implements PetPort {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PersonAdapter personAdapter;

    @Override
    public void savePet(Pet pet) {
        PetEntity petEntity = petEntityAdapter(pet);
        petRepository.save(petEntity);
        pet.setPetId(petEntity.getPetId());
    }

    @Override
    public List<Pet> getPetsByPerson(Person person) {
        PersonEntity personEntity = personAdapter.personEntityAdapter(person);
        List<PetEntity> petEntities = petRepository.getPetsByOwner(personEntity);
        return toPetList(petEntities);
    }

    public Pet petAdapter(PetEntity petEntity) {
        Pet pet = new Pet();
        pet.setName(petEntity.getName());
        pet.setAge(petEntity.getAge());
        pet.setBreed(petEntity.getBreed());
        pet.setCharacteristics(petEntity.getCharacteristics());
        pet.setWeight(petEntity.getWeight());

        if (petEntity.getOwner() != null) {
            Person owner = personAdapter.personAdapter(petEntity.getOwner());
            pet.setOwner(owner);
        }

        return pet;
    }

    public PetEntity petEntityAdapter(Pet pet) {
        PetEntity petEntity = new PetEntity();
        petEntity.setPetId(pet.getPetId());
        petEntity.setName(pet.getName());
        petEntity.setAge(pet.getAge());
        petEntity.setBreed(pet.getBreed());
        petEntity.setCharacteristics(pet.getCharacteristics());
        petEntity.setWeight(pet.getWeight());

        if (pet.getOwner() != null) {
            PersonEntity ownerEntity = personAdapter.personEntityAdapter(pet.getOwner());
            petEntity.setOwner(ownerEntity);
        }

        return petEntity;
    }

    public List<Pet> toPetList(List<PetEntity> petEntities) {
        if (petEntities == null || petEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return petEntities.stream()
                .map(this::toPet)
                .collect(Collectors.toList());
    }

    public Pet toPet(PetEntity petEntity) {
        if (petEntity == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setPetId(petEntity.getPetId());
        pet.setName(petEntity.getName());
        pet.setAge(petEntity.getAge());
        pet.setBreed(petEntity.getBreed());
        pet.setCharacteristics(petEntity.getCharacteristics());
        pet.setWeight(petEntity.getWeight());
        pet.setOwner(personAdapter.personAdapter(petEntity.getOwner()));
        return pet;
    }

}
