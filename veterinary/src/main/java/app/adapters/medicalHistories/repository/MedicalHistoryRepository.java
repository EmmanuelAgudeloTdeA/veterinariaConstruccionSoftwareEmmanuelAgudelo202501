package app.adapters.medicalHistories.repository;

import app.adapters.medicalHistories.entity.MedicalHistoryEntity;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
    List<MedicalHistoryEntity> findMedicalHistoriesByPet(PetEntity petEntity);
}
