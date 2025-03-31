package app.domain.services;

import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.User;
import app.ports.AdminPort;
import app.ports.PersonPort;
import app.ports.PetPort;
import app.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@Service
public class AdminService {

    @Autowired
    private PersonPort personPort;
    @Autowired
    private UserPort userPort;
    private AdminPort adminPort;
    @Autowired
    private PetPort petPort;


    public void registerUser(User user) throws Exception {
        if (personPort.existPerson(user.getDocument())) {
            throw new Exception("ya existe una persona con esa cedula");
        }
        if (userPort.existsUserName(user.getUserName())) {
            throw new Exception("ya existe ese username registrado");
        }
        personPort.savePerson(user);
        userPort.saveUser(user);
    }

    public void registerPerson(Person person) throws Exception {
        if (personPort.existPerson(person.getDocument())) {
            throw new Exception("ya existe una persona con esa cedula");
        }
        personPort.savePerson(person);
    }

    public void registerPet(Pet pet) throws Exception {
        if (!personPort.existPerson(pet.getOwner().getDocument())) {
            throw new Exception("no existe una persona con esa cedula");
        }
        if (!pet.getOwner().getRole().equals("owner")) {
            throw new Exception("el usuario no es un dueño");
        }
        petPort.savePet(pet);
    }

}
