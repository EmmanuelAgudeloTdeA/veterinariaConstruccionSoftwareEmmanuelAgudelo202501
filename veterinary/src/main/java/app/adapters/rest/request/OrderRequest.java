package app.adapters.rest.request;

import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class OrderRequest {
    private Person owner;
    private Person veterinarian;
    private String medicationName;
    private String medicationDosage;
    private Timestamp date;
}
