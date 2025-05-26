package app.adapters.invoices;

import app.adapters.invoices.entity.InvoiceEntity;
import app.adapters.invoices.repository.InvoiceRepository;
import app.adapters.orders.OrderAdapter;
import app.adapters.orders.entity.OrderEntity;
import app.adapters.persons.PersonAdapter;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.pets.PetAdapter;
import app.adapters.pets.entity.PetEntity;
import app.domain.models.Invoice;
import app.ports.InvoicePort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
@NoArgsConstructor
public class InvoiceAdapter implements InvoicePort {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PetAdapter petAdapter;
    @Autowired
    private PersonAdapter personAdapter;
    @Autowired
    private OrderAdapter orderAdapter;

    @Override
    public void saveInvoice(Invoice invoice) throws Exception {
        InvoiceEntity invoiceEntity = invoiceEntityAdapter(invoice);
        invoiceRepository.save(invoiceEntity);
        invoice.setInvoiceId(invoiceEntity.getInvoiceId());
    }

    private InvoiceEntity invoiceEntityAdapter(Invoice invoice) throws Exception {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        PetEntity petEntity = petAdapter.petEntityAdapter(invoice.getPet());
        PersonEntity personEntity = personAdapter.personEntityAdapter(invoice.getOwner());
        OrderEntity orderEntity = orderAdapter.orderEntityAdapter(invoice.getOrder());
        invoiceEntity.setInvoiceId(invoice.getInvoiceId());
        if (invoice.getPet() == null) {
            throw new Exception("la mascota es requerida");
        }
        invoiceEntity.setPet(petEntity);
        if (invoice.getOwner() == null) {
            throw new Exception("el dueño es requerido");
        }
        invoiceEntity.setOwner(personEntity);
        if (invoice.getOrder() != null) {
            invoiceEntity.setOrder(orderEntity);
        }
        invoiceEntity.setProductName(invoice.getProductName());
        invoiceEntity.setPrice(invoice.getPrice());
        invoiceEntity.setAmount(invoice.getAmount());
        invoiceEntity.setDate(invoice.getDate());
        return invoiceEntity;
    }
}
