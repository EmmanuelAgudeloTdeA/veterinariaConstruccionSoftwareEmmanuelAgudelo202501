package app.ports;

import app.domain.models.MedicalHistory;
import app.domain.models.Pet;

import java.util.List;


public interface MedicalHistoryPort {
    public void saveMedicalHistory(MedicalHistory medicalHistory);

    public List<MedicalHistory> findMedicalHistoriesByPet(Pet pet);
}
