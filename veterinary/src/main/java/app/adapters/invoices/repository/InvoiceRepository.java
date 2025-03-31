package app.adapters.invoices.repository;

import app.adapters.invoices.entity.InvoiceEntity;
import app.adapters.persons.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    InvoiceEntity findByOwner(PersonEntity personEntity);
}
