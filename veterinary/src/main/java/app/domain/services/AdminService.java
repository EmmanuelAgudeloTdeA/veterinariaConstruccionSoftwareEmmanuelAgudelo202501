    package app.domain.services;

    import app.domain.models.Person;
    import app.domain.models.Pet;
    import app.domain.models.User;
    import app.exceptions.BusinessException;
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
                throw new BusinessException("ya existe una persona con esa cedula");
            }
            if (userPort.existsUserName(user.getUserName())) {
                throw new BusinessException("ya existe ese username registrado");
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

        public User buildUser(String role, long document, String name, Date dateOfBirth, String userName, String password) {
            User user = new User();
            user.setRole(role);
            user.setDocument(document);
            user.setName(name);
            user.setDateOfBirthday(dateOfBirth);
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }

        public Person buildPerson( long document, String name, Date dateOfBirth, String role) {
            Person person = new Person();
            person.setRole(role);
            person.setDocument(document);
            person.setName(name);
            person.setDateOfBirthday(dateOfBirth);
            return person;
        }

        public Pet buildPet(String name, Person owner, Date birthDate, String breed, String characteristics, float weight) {
            Pet pet = new Pet();
            pet.setName(name);
            pet.setOwner(owner);
            pet.setAge(birthDate);
            pet.setBreed(breed);
            pet.setCharacteristics(characteristics);
            pet.setWeight(weight);
            return pet;
        }

        public void validateOwner(Person person) throws Exception {
            if (person == null) throw new Exception("No existe una persona con esa cédula");
            if (!"owner".equals(person.getRole())) throw new Exception("El usuario no es un dueño");
        }
    }
