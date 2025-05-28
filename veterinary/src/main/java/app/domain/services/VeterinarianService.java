package app.domain.services;

import app.adapters.utils.Utils;
import app.adapters.rest.request.MedicalHistoryRequest;
import app.domain.models.*;
import app.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarianService {
    private final PersonPort personPort;
    private final UserPort userPort;
    private final PetPort petPort;
    private final MedicalHistoryPort medicalHistoryPort;
    private final OrderPort orderPort;

    public void registerClinicalHistory(MedicalHistoryRequest request) {
        Person owner = personPort.findByDocument(request.getOwnerDocument());
        Person veterinarianPerson = personPort.findByDocument(request.getVeterinarianDocument());
        User veterinarian = userPort.findByPerson(veterinarianPerson);
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");
        List<Pet> pets = petPort.getPetsByPerson(owner);
        if (pets.isEmpty() || request.getPetIndex() < 0 || request.getPetIndex() >= pets.size())
            throw new RuntimeException("Mascota inválida.");
        System.out.println(pets);
        Pet selectedPet = pets.get(request.getPetIndex());

        Order order = new Order();
        order.setOwner(owner);
        order.setVeterinarian(veterinarian);
        order.setMedicationName(request.getPrescribedMedication());
        order.setMedicationDosage(request.getMedicationDosage());
        order.setDate(Utils.getCurrentDate());
        orderPort.saveOrder(order);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDate(Utils.getCurrentDate());
        medicalHistory.setVeterinarian(veterinarian);
        medicalHistory.setPet(selectedPet);
        medicalHistory.setReasonForConsultation(request.getReasonForConsultation());
        medicalHistory.setSymptoms(request.getSymptoms());
        medicalHistory.setDiagnosis(request.getDiagnosis());
        medicalHistory.setTreatment(request.getTreatment());
        medicalHistory.setPrescribedMedication(request.getPrescribedMedication());
        medicalHistory.setMedicationDosage(request.getMedicationDosage());
        medicalHistory.setVaccinationHistory(request.getVaccinationHistory());
        medicalHistory.setMedicationAllergies(request.getMedicationAllergies());
        medicalHistory.setTreatmentDetails(request.getTreatmentDetails());
        medicalHistory.setOrder(order);
        medicalHistory.setOrderCancellation(false);

        medicalHistoryPort.saveMedicalHistory(medicalHistory);
    }

    public List<MedicalHistory> getMedicalHistories(long ownerDocument, int petId) {
        Person owner = personPort.findByDocument(ownerDocument);
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");
        List<Pet> pets = petPort.getPetsByPerson(owner);

        // Buscar la mascota por ID
        Pet selectedPet = null;
        for (Pet pet : pets) {
            if (pet.getPetId() == petId) {
                selectedPet = pet;
                break;
            }
        }

        if (selectedPet == null) throw new RuntimeException("Mascota no encontrada.");

        return medicalHistoryPort.findMedicalHistoriesByPet(selectedPet);
    }

    public Order getOrderByOwnerDocument(long document) {
        Person owner = personPort.findByDocument(document);
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");

        Order order = orderPort.findByOwner(owner);
        if (order == null) throw new RuntimeException("Orden no encontrada.");

        return order;
    }
}
