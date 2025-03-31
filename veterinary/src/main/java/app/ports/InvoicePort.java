package app.ports;

import app.domain.models.Invoice;

public interface InvoicePort {
    public void saveInvoice(Invoice invoice) throws Exception;
}
