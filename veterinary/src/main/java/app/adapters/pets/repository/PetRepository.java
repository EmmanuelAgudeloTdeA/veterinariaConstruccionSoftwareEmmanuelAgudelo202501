package app.adapters.pets.repository;

import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import app.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> getPetsByOwner(PersonEntity person);
}
