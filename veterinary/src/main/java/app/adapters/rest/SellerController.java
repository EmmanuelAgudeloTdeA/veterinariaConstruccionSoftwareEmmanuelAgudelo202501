package app.adapters.rest;

import app.adapters.rest.request.InvoiceRequest;
import app.domain.models.Invoice;
import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.*;
import app.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private PersonPort personPort;

    @Autowired
    private PetPort petPort;

    @Autowired
    private OrderPort orderPort;

    @Autowired
    private InvoicePort invoicePort;

    @PostMapping("/consult-order")
    public ResponseEntity<?> consultOrder(@RequestParam long document) {
        try {
            Person person = personPort.findByDocument(document);
            if (person == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la persona");
            }

            Order order = orderPort.findByOwner(person);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una orden asociada a esta persona");
            }

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar la orden: " + e.getMessage());
        }
    }

    @PostMapping("/invoice/create")
    public ResponseEntity<?> registerInvoice(@RequestBody InvoiceRequest request) {
        try {
            Person owner = personPort.findByDocument(request.getOwner().getDocument());
            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el cliente");
            }

            Order order = orderPort.findByOwner(owner);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una orden asociada a este cliente");
            }

            List<Pet> pets = petPort.getPetsByPerson(owner);
            if (pets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este cliente no tiene mascotas registradas");
            }

            Pet selectedPet = pets.stream()
                    .filter(p -> p.getPetId() == request.getPet().getPetId())
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("Mascota seleccionada inválida"));

            Invoice invoice = new Invoice();
            invoice.setOrder(order);
            invoice.setPet(selectedPet);
            invoice.setOwner(owner);
            invoice.setProductName(request.getProductName());
            invoice.setPrice(request.getPrice());
            invoice.setAmount(request.getAmount());
            invoice.setDate(request.getDate());

            invoicePort.saveInvoice(invoice);
            return ResponseEntity.ok("Factura registrada con éxito");

        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(be.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la factura: " + e.getMessage());
        }
    }
}