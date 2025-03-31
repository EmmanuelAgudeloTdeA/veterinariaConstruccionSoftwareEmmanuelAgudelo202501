package app.adapters.inputs;

import app.adapters.inputs.utils.InvoiceValidator;
import app.adapters.inputs.utils.PersonValidator;
import app.adapters.inputs.utils.Utils;
import app.domain.models.Invoice;
import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.ports.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Component
public class SellerInput implements InputPort {

    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private PetPort petPort;
    @Autowired
    private PersonPort personPort;
    @Autowired
    private OrderPort orderPort;
    @Autowired
    private InvoicePort invoicePort;

    private final String MENU = """
            Menú Vendedor
            1. Consultar Orden
            2. Registro de factura
            3. Salir""";

    public void menu() throws Exception {
        boolean sesion = true;
        while (sesion) {
            sesion = options();
        }
    }

    private boolean options() throws Exception {
        try {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();
            switch (option) {
                case "1":
                    Order order = consultOrder();
                    System.out.println("------ Orden encontrada -------");
                    System.out.println("Dueño: " + order.getOwner().getName());
                    System.out.println("Veterinario: " + order.getVeterinarian().getName());
                    System.out.println("Diagnóstico: " + order.getMedicationDosage());
                    System.out.println("Medicamento: " + order.getMedicationName());
                    System.out.println("Fecha de creación: " + order.getDate());
                    return true;
                case "2":
                    registerInvoice();
                    return true;
                case "3":
                    System.out.println("Saliendo...");
                    return false;
                default:
                    System.out.println("Opción inválida.");
                    return true;
            }
        } catch (Exception e) {
            System.out.println("Algo salio mal: "+e.getMessage());
            return true;
        }
    }

    public Order consultOrder() throws Exception {
        System.out.println("Ingrese el documento del cliente");
        long document = personValidator.documentValidator(Utils.getReader().nextLine());

        Person person = personPort.findByDocument(document);
        if (person == null) {
            throw new Exception("No se encontró ninguna persona con ese documento.");
        }
        Order order = orderPort.findByOwner(person);
        if (order == null) {
            throw new Exception("No se encontró una orden asociada a esta persona.");
        }
        return order;
    }

    public void registerInvoice() throws Exception {
        Order order = consultOrder();
        List<Pet> pets = petPort.getPetsByPerson(order.getOwner());
        if (pets.isEmpty()) {
            throw new Exception("Este cliente no tiene mascotas registradas.");
        }
        System.out.println("Seleccione la mascota:");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i).getName() + " - " + pets.get(i).getBreed());
        }
        int selectedIndex = Utils.getReader().nextInt();
        Utils.getReader().nextLine();
        if (selectedIndex < 1 || selectedIndex > pets.size()) {
            throw new Exception("Selección inválida.");
        }
        Pet selectedPet = pets.get(selectedIndex - 1);

        System.out.println("Ingrese el nombre del producto");
        String productName = invoiceValidator.productNameValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese el precio del producto");
        float price = invoiceValidator.priceValidator(Utils.getReader().nextFloat());
        System.out.println("Ingrese la cantidad del producto");
        int amount = invoiceValidator.amountValidator(Utils.getReader().nextInt());

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setPet(selectedPet);
        invoice.setOwner(order.getOwner());
        invoice.setProductName(productName);
        invoice.setPrice(price);
        invoice.setAmount(amount);
        invoice.setDate(Utils.getCurrentDate());
        invoicePort.saveInvoice(invoice);
        System.out.println("Factura registrada con éxito.");
    }
}
