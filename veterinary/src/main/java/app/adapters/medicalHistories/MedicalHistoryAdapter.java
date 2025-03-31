package app.adapters.medicalHistories;

import app.adapters.medicalHistories.entity.MedicalHistoryEntity;
import app.adapters.medicalHistories.repository.MedicalHistoryRepository;
import app.adapters.orders.OrderAdapter;
import app.adapters.orders.entity.OrderEntity;
import app.adapters.persons.PersonAdapter;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.PetAdapter;
import app.adapters.pets.entity.PetEntity;
import app.domain.models.MedicalHistory;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.MedicalHistoryPort;
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
public class MedicalHistoryAdapter implements MedicalHistoryPort {
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;
    @Autowired
    private PersonAdapter personAdapter;
    @Autowired
    private OrderAdapter orderAdapter;
    @Autowired
    private PetAdapter petAdapter;

    @Override
    public void saveMedicalHistory(MedicalHistory medicalHistory) {
        MedicalHistoryEntity medicalHistoryEntity = medicalHistoryEntityAdapter(medicalHistory);
        medicalHistoryRepository.save(medicalHistoryEntity);
        medicalHistory.setMedicalHistoryId(medicalHistoryEntity.getMedicalHistoryId());
    }

    @Override
    public List<MedicalHistory> findMedicalHistoriesByPet(Pet pet) {
        PetEntity petEntity = petAdapter.petEntityAdapter(pet);
        List<MedicalHistoryEntity> medicalHistoryEntities = medicalHistoryRepository.findMedicalHistoriesByPet(petEntity);
        if (medicalHistoryEntities.isEmpty()) {
            return null;
        }
        return toMedicalHistoryList(medicalHistoryEntities);
    }

    public List<MedicalHistory> toMedicalHistoryList(List<MedicalHistoryEntity> medicalHistoryEntities) {
        if (medicalHistoryEntities == null) {
            return Collections.emptyList();
        }
        return medicalHistoryEntities.stream().map(this::toMedicalHistory).collect(Collectors.toList());
    }

    public MedicalHistory toMedicalHistory(MedicalHistoryEntity medicalHistoryEntity) {
        if (medicalHistoryEntity == null) {
            return null;
        }
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setMedicalHistoryId(medicalHistoryEntity.getMedicalHistoryId());
        medicalHistory.setDate(medicalHistoryEntity.getDate());
        if (medicalHistoryEntity.getVeterinarian() != null) {
            Person veterinarian = personAdapter.personAdapter(medicalHistoryEntity.getVeterinarian());
            medicalHistory.setVeterinarian(veterinarian);
        }
        if (medicalHistoryEntity.getPet() != null) {
            Pet pet = petAdapter.petAdapter(medicalHistoryEntity.getPet());
            medicalHistory.setPet(pet);
        }
        medicalHistory.setReasonForConsultation(medicalHistoryEntity.getReasonForConsultation());
        medicalHistory.setSymptoms(medicalHistoryEntity.getSymptoms());
        medicalHistory.setDiagnosis(medicalHistoryEntity.getDiagnosis());
        medicalHistory.setTreatment(medicalHistoryEntity.getTreatment());
        medicalHistory.setPrescribedMedication(medicalHistoryEntity.getPrescribedMedication());
        medicalHistory.setMedicationDosage(medicalHistoryEntity.getMedicationDosage());
        if (medicalHistoryEntity.getOrder() != null) {
            app.domain.models.Order order = orderAdapter.orderAdapter(medicalHistoryEntity.getOrder());
            medicalHistory.setOrder(order);
        }
        medicalHistory.setVaccinationHistory(medicalHistoryEntity.getVaccinationHistory());
        medicalHistory.setMedicationAllergies(medicalHistoryEntity.getMedicationAllergies());
        medicalHistory.setTreatmentDetails(medicalHistoryEntity.getTreatmentDetails());
        medicalHistory.setOrderCancellation(medicalHistoryEntity.getOrderCancellation());
        return medicalHistory;
    }

    private MedicalHistory medicalHistoryAdapter(MedicalHistoryEntity medicalHistoryEntity) {
        if (medicalHistoryEntity == null) {
            return null;
        }
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setMedicalHistoryId(medicalHistoryEntity.getMedicalHistoryId());
        medicalHistory.setDate(medicalHistoryEntity.getDate());
        if (medicalHistoryEntity.getVeterinarian() != null) {
            Person veterinarian = personAdapter.personAdapter(medicalHistoryEntity.getVeterinarian());
            medicalHistory.setVeterinarian(veterinarian);
        }
        if (medicalHistoryEntity.getPet() != null) {
            Pet pet = petAdapter.petAdapter(medicalHistoryEntity.getPet());
            medicalHistory.setPet(pet);
        }
        medicalHistory.setReasonForConsultation(medicalHistoryEntity.getReasonForConsultation());
        medicalHistory.setSymptoms(medicalHistoryEntity.getSymptoms());
        medicalHistory.setDiagnosis(medicalHistoryEntity.getDiagnosis());
        medicalHistory.setTreatment(medicalHistoryEntity.getTreatment());
        medicalHistory.setPrescribedMedication(medicalHistoryEntity.getPrescribedMedication());
        medicalHistory.setMedicationDosage(medicalHistoryEntity.getMedicationDosage());
        if (medicalHistoryEntity.getOrder() != null) {
            app.domain.models.Order order = orderAdapter.orderAdapter(medicalHistoryEntity.getOrder());
            medicalHistory.setOrder(order);
        }
        medicalHistory.setVaccinationHistory(medicalHistoryEntity.getVaccinationHistory());
        medicalHistory.setMedicationAllergies(medicalHistoryEntity.getMedicationAllergies());
        medicalHistory.setTreatmentDetails(medicalHistoryEntity.getTreatmentDetails());
        medicalHistory.setOrderCancellation(medicalHistoryEntity.getOrderCancellation());
        return medicalHistory;
    }

    private MedicalHistoryEntity medicalHistoryEntityAdapter(MedicalHistory medicalHistory) {
        MedicalHistoryEntity medicalHistoryEntity = new MedicalHistoryEntity();
        medicalHistoryEntity.setMedicalHistoryId(medicalHistory.getMedicalHistoryId());
        medicalHistoryEntity.setDate(medicalHistory.getDate());
        if (medicalHistory.getVeterinarian() != null) {
            PersonEntity ownerEntity = personAdapter.personEntityAdapter(medicalHistory.getVeterinarian());
            medicalHistoryEntity.setVeterinarian(ownerEntity);
        }
        if (medicalHistory.getPet() != null) {
            PetEntity petEntity = petAdapter.petEntityAdapter(medicalHistory.getPet());
            medicalHistoryEntity.setPet(petEntity);
        }
        medicalHistoryEntity.setReasonForConsultation(medicalHistory.getReasonForConsultation());
        medicalHistoryEntity.setSymptoms(medicalHistory.getSymptoms());
        medicalHistoryEntity.setDiagnosis(medicalHistory.getDiagnosis());
        medicalHistoryEntity.setTreatment(medicalHistory.getTreatment());
        medicalHistoryEntity.setPrescribedMedication(medicalHistory.getPrescribedMedication());
        medicalHistoryEntity.setMedicationDosage(medicalHistory.getMedicationDosage());
        if (medicalHistory.getOrder() != null) {
            OrderEntity orderEntity = orderAdapter.orderEntityAdapter(medicalHistory.getOrder());
            medicalHistoryEntity.setOrder(orderEntity);
        }
        medicalHistoryEntity.setVaccinationHistory(medicalHistory.getVaccinationHistory());
        medicalHistoryEntity.setMedicationAllergies(medicalHistory.getMedicationAllergies());
        medicalHistoryEntity.setTreatmentDetails(medicalHistory.getTreatmentDetails());
        medicalHistoryEntity.setOrderCancellation(medicalHistory.getOrderCancellation());
        return medicalHistoryEntity;
    }
}
