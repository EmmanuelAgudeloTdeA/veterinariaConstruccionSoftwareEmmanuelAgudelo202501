package app.adapters.medicalHistories.entity;

import app.adapters.orders.entity.OrderEntity;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.entity.PetEntity;
import app.adapters.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "medical_histories")
public class MedicalHistoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long medicalHistoryId;
    @Column(name = "date")
    private Timestamp date;
    @JoinColumn(name = "veterinarian_id")
    @ManyToOne
    private UserEntity veterinarian;
    @JoinColumn(name = "pet_id")
    @ManyToOne
    private PetEntity pet;
    @Column(name = "reason_for_consultation")
    private String reasonForConsultation;
    @Column(name = "symptoms")
    private String symptoms;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "treatment")
    private String treatment;
    @Column(name = "prescribed_medication")
    private String prescribedMedication;
    @Column(name = "medication_dosage")
    private String medicationDosage;
    @JoinColumn(name = "order_id")
    @OneToOne()
    private OrderEntity order;
    @Column(name = "vaccination_history")
    private String vaccinationHistory;
    @Column(name = "medication_allergies")
    private String medicationAllergies;
    @Column(name = "treatment_details")
    private String treatmentDetails;
    @Column(name = "order_cancellation")
    private boolean orderCancellation;


    public boolean getOrderCancellation() {
        return orderCancellation;
    }
}
