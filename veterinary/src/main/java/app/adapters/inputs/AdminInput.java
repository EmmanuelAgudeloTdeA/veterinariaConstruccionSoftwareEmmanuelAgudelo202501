package app.adapters.inputs;


import app.adapters.inputs.utils.PersonValidator;
import app.adapters.inputs.utils.PetValidator;
import app.adapters.inputs.utils.UserValidator;
import app.adapters.inputs.utils.Utils;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.User;
import app.domain.services.AdminService;
import app.ports.InputPort;
import app.ports.PersonPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Setter
@Getter
@NoArgsConstructor
@Component
public class AdminInput implements InputPort {

    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PersonPort personPort;
    @Autowired
    private PetValidator petValidator;
    @Autowired
    private SellerInput sellerInput;

    private final String MENU = """
            Ingrese la opcion:
             1. Registrar veterinario.
             2. Registrar vendedor.
             3. Registrar dueño.
             4. Registrar mascota.
             5. Registrar factura de venta.
             6. Salir.""";


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
                    this.registerVeterinarian();
                    return true;
                case "2":
                    this.registerSeller();
                    return true;
                case "3":
                    this.registerOwner();
                    return true;
                case "4":
                    this.registerPet();
                    return true;
                case "5":
                    sellerInput.registerInvoice();
                    return true;
                case "6":
                    System.out.println("Saliendo...");
                    return false;
                default:
                    System.out.println("Opcion no valida");
                    return true;
            }
        } catch (Exception e) {
            System.out.println("Algo salio mal: " + e.getMessage());
            return true;
        }
    }

    public void registerVeterinarian() throws Exception {
        User veterinarian = readUserData("veterinarian", "veterinario");
        adminService.registerUser(veterinarian);
        System.out.println("Veterinario registrado con éxito");
    }

    public void registerSeller() throws Exception {
        User seller = readUserData("seller", "vendedor");
        adminService.registerUser(seller);
        System.out.println("Vendedor registrado con éxito");
    }

    public void registerOwner() throws Exception {
        User owner = readUserData("owner", "dueño");
        adminService.registerPerson(owner);
        System.out.println("Dueño registrado con éxito");
    }

    public User readUserData(String role, String lable) throws Exception {
        System.out.println("Ingrese el documento del " + lable);
        long document = personValidator.documentValidator(Utils.getReader().nextLine());

        System.out.println("Ingrese el nombre del " + lable);
        String name = personValidator.nameValidator(Utils.getReader().nextLine());

        System.out.println("Ingrese la fecha de nacimiento del " + lable + " (\"dd/MM/yyyy\"): ");
        String textDate = Utils.getReader().nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirthday = new Date(sdf.parse(textDate).getTime());
        dateOfBirthday = new Date(personValidator.dateValidator(dateOfBirthday).getTime());
        if (!role.equals("owner")) {
            System.out.println("Ingrese el nombre de usuario del " + lable);
            String userName = userValidator.userNameValidator(Utils.getReader().nextLine());

            System.out.println("Ingrese la contraseña del " + lable);
            String password = userValidator.passwordValidator(Utils.getReader().nextLine());

            User user = new User();
            user.setDocument(document);
            user.setName(name);
            user.setDateOfBirthday(dateOfBirthday);
            user.setRole(role);
            user.setUserName(userName);
            user.setPassword(password);

            return user;
        } else {
            User user = new User();
            user.setDocument(document);
            user.setName(name);
            user.setDateOfBirthday(dateOfBirthday);
            user.setRole(role);

            return user;
        }
    }

    public void registerPet() throws Exception {
        System.out.println("Ingrese el nombre de la mascota");
        String name = petValidator.nameValidator(Utils.getReader().nextLine());

        System.out.println("Ingrese la cedula del dueño de la mascota");
        long document = personValidator.documentValidator(Utils.getReader().nextLine());
        Person owner = personPort.findByDocument(document);
        if (personPort.findByDocument(document) == null) {
            throw new Exception("No existe una persona con esa cédula");
        }

        System.out.println("Ingrese la fecha de nacimiento de la mascota (\"dd/MM/yyyy\"): ");
        String textDate = Utils.getReader().nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirthday = new Date(sdf.parse(textDate).getTime());
        dateOfBirthday = new Date(petValidator.ageValidator(dateOfBirthday).getTime());

        System.out.println("Ingrese la raza de la mascota");
        String breed = petValidator.breedValidator(Utils.getReader().nextLine());

        System.out.println("Ingrese las características de la mascota");
        String characteristics = petValidator.characteristicsValidator(Utils.getReader().nextLine());

        System.out.println("Ingrese el peso de la mascota");
        float weight = petValidator.weightValidator(Utils.getReader().nextFloat());

        Pet pet = new Pet();
        pet.setName(name);
        pet.setOwner(owner);
        pet.setAge(dateOfBirthday);
        pet.setBreed(breed);
        pet.setCharacteristics(characteristics);
        pet.setWeight(weight);

        adminService.registerPet(pet);
        System.out.println("Mascota registrada con éxito");
    }
}

