package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class MedicalHistory {
    private long medicalHistoryId;
    private Timestamp date;
    private User veterinarian;
    private Pet pet;
    private String reasonForConsultation;
    private String symptoms;
    private String diagnosis;
    private String treatment;
    private String prescribedMedication;
    private String medicationDosage;
    private Order order;
    private String vaccinationHistory;
    private String medicationAllergies;
    private String treatmentDetails;
    private boolean orderCancellation;

    public MedicalHistory(long medicalHistoryId, Timestamp date, User veterinarian, Pet pet, String reasonForConsultation, String symptoms, String diagnosis, String treatment, String prescribedMedication, String medicationDosage, Order order, String vaccinationHistory, String medicationAllergies, String treatmentDetails, boolean orderCancellation) {
        this.medicalHistoryId = medicalHistoryId;
        this.date = date;
        this.veterinarian = veterinarian;
        this.pet = pet;
        this.reasonForConsultation = reasonForConsultation;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescribedMedication = prescribedMedication;
        this.medicationDosage = medicationDosage;
        this.order = order;
        this.vaccinationHistory = vaccinationHistory;
        this.medicationAllergies = medicationAllergies;
        this.treatmentDetails = treatmentDetails;
        this.orderCancellation = orderCancellation;
    }

    public boolean getOrderCancellation() {
        return orderCancellation;
    }
}
