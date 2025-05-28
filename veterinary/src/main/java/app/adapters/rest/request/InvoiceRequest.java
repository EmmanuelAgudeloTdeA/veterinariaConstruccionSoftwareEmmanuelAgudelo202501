package app.adapters.rest.request;

import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
public class InvoiceRequest {
    private long petId;
    private long ownerDocument;
    private long orderId;
    private String productName;
    private float price;
    private int amount;
    private Timestamp date;
}
