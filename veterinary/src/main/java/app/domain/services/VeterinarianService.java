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
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");

        List<Pet> pets = petPort.getPetsByPerson(owner);
        if (pets.isEmpty() || request.getPetIndex() < 0 || request.getPetIndex() >= pets.size())
            throw new RuntimeException("Mascota inválida.");

        Pet selectedPet = pets.get(request.getPetIndex());

        Order order = new Order();
        order.setOwner(owner);
        order.setVeterinarian(request.getVeterinarian());
        order.setMedicationName(request.getPrescribedMedication());
        order.setMedicationDosage(request.getMedicationDosage());
        order.setDate(Utils.getCurrentDate());
        orderPort.saveOrder(order);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDate(Utils.getCurrentDate());
        medicalHistory.setVeterinarian(request.getVeterinarian());
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

    public List<MedicalHistory> getMedicalHistories(long ownerDocument, int petIndex) {
        Person owner = personPort.findByDocument(ownerDocument);
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");

        List<Pet> pets = petPort.getPetsByPerson(owner);
        if (pets.isEmpty() || petIndex < 0 || petIndex >= pets.size())
            throw new RuntimeException("Mascota inválida.");

        return medicalHistoryPort.findMedicalHistoriesByPet(pets.get(petIndex));
    }

    public Order getOrderByOwnerDocument(long document) {
        Person owner = personPort.findByDocument(document);
        if (owner == null) throw new RuntimeException("Dueño no encontrado.");

        Order order = orderPort.findByOwner(owner);
        if (order == null) throw new RuntimeException("Orden no encontrada.");

        return order;
    }
}
