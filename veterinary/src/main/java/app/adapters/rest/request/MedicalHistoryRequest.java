package app.adapters.rest.request;

import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class MedicalHistoryRequest {
    private long ownerDocument;
    private int petIndex;
    private long veterinarianDocument;
    private Timestamp date;
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
}
