package app.adapters.persons.repository;

import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    boolean existsByDocument(long document);
    PersonEntity findByDocument(long document);
}
