package app.adapters.rest;

import app.adapters.utils.PersonValidator;
import app.adapters.utils.PetValidator;
import app.adapters.utils.UserValidator;
import app.adapters.rest.request.PetRequest;
import app.adapters.rest.request.UserRequest;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.User;
import app.domain.services.AdminService;
import app.exceptions.BusinessException;
import app.ports.PersonPort;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Setter
@Getter
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    PersonValidator personValidator;
    @Autowired
    PetValidator petValidator;
    @Autowired
    private PersonPort personPort;
    @Autowired
    private UserValidator userValidator;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @GetMapping("/")
    public ResponseEntity<String> welcome() {
        String message = """
                Bienvenido al sistema de administración de la veterinaria 🐾<br><br>
                📌 Rutas disponibles:<br><br>
                 🛠️ Administración (Admin):<br>
                - POST /veterinarian/create: Crear usuario veterinario<br>
                - POST /seller/create: Crear usuario vendedor<br>
                - POST /owner/create: Crear cliente (dueño de mascota)<br>
                - POST /pet/create: Registrar mascota<br><br>
                👨‍⚕️ Veterinario:<br>
                - POST /veterinarian/clinical-history: Registrar historia clínica<br>
                - GET /veterinarian/clinical-history: Consultar historia clínica<br>
                - GET /veterinarian/order/{document}: Consultar orden<br><br>
                🧾 Vendedor:<br>
                - GET /seller/order/{document}: Consultar orden<br>
                - POST /seller/invoice: Registrar factura<br><br>
                🚨 Notas:<br>
                - El campo {document} representa el número de documento del cliente o usuario.<br><br>
                ¡Gracias por usar nuestro sistema! 🐶🐱
                """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(message);
    }

    @PostMapping("/veterinarian/create")
    public ResponseEntity<?> createVeterinarian(@RequestBody UserRequest request) {
        return createUserWithRole(request, "veterinarian");
    }

    @PostMapping("/seller/create")
    public ResponseEntity<?> createSeller(@RequestBody UserRequest request) {
        return createUserWithRole(request, "seller");
    }

    @PostMapping("/owner/create")
    public ResponseEntity<?> createOwner(@RequestBody UserRequest request) {
        try {
            // Validaciones
            personValidator.documentValidator(String.valueOf(request.getDocument()));
            personValidator.nameValidator(request.getName());
            Date birthDate = new Date(sdf.parse(request.getDateOfBirthday()).getTime());
            birthDate = new Date(personValidator.dateValidator(birthDate).getTime());

            Person owner;
            owner = adminService.buildPerson(
                    request.getDocument(),
                    request.getName(),
                    birthDate,
                    "owner"
            );
            adminService.registerPerson(owner);
            return ResponseEntity.ok(owner);

        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(be.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/pet/create")
    public ResponseEntity<?> createPet(@RequestBody PetRequest request) {
        try {
            // Validaciones
            Date age = new Date(sdf.parse(request.getAge()).getTime());
            personValidator.documentValidator(String.valueOf(request.getOwner().getDocument()));
            petValidator.nameValidator(request.getName());
            petValidator.breedValidator(request.getBreed());
            petValidator.ageValidator(age);
            petValidator.characteristicsValidator(request.getCharacteristics());
            petValidator.weightValidator(request.getWeight());

            // Buscar dueño
            Person owner = personPort.findByDocument(request.getOwner().getDocument());
            adminService.validateOwner(owner);
            age = new Date(petValidator.ageValidator(age).getTime());

            // Crear y registrar mascota
            Pet pet = adminService.buildPet(
                    request.getName(),
                    owner,
                    age,
                    request.getBreed(),
                    request.getCharacteristics(),
                    request.getWeight()
            );

            adminService.registerPet(pet);
            return ResponseEntity.ok(pet);

        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(be.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<?> createUserWithRole(UserRequest request, String role) {
        try {
            // Validaciones
            personValidator.documentValidator(String.valueOf(request.getDocument()));
            personValidator.nameValidator(request.getName());
            userValidator.userNameValidator(request.getUserName());
            userValidator.passwordValidator(request.getPassword());

            Date birthDate = new Date(sdf.parse(request.getDateOfBirthday()).getTime());
            birthDate = new Date(personValidator.dateValidator(birthDate).getTime());

            User user = adminService.buildUser(
                    role,
                    request.getDocument(),
                    request.getName(),
                    birthDate,
                    request.getUserName(),
                    request.getPassword()
            );

            adminService.registerUser(user);
            return ResponseEntity.ok(user);

        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(be.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
