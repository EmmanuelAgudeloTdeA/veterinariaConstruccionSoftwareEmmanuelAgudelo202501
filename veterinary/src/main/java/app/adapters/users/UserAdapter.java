package app.adapters.users;

import app.adapters.persons.PersonAdapter;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.users.entity.UserEntity;
import app.adapters.users.repository.UserRepository;
import app.domain.models.Person;
import app.domain.models.User;
import app.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
public class UserAdapter implements UserPort {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonAdapter personAdapter;

    @Override
    public boolean existsUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userEntityAdapter(user);
        userRepository.save(userEntity);
        user.setUserId(userEntity.getUserId());
    }

    @Override
    public User findByPerson(Person person) {
        PersonEntity personEntity = personAdapter.personEntityAdapter(person);
        UserEntity userEntity = userRepository.findByPerson(personEntity);
        return userAdapter(userEntity);
    }

    @Override
    public User findByUserName(User user) {
        UserEntity userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity == null) {
            return null;
        }
        return userAdapter(userEntity);
    }

    private User userAdapter(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.setPersonId(userEntity.getPerson().getPersonId());
        user.setDocument(userEntity.getPerson().getDocument());
        user.setName(userEntity.getPerson().getName());
        user.setDateOfBirthday(userEntity.getPerson().getDateOfBirthday());
        user.setRole(userEntity.getPerson().getRole());

        user.setUserId(userEntity.getUserId());
        user.setUserName(userEntity.getUserName());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    private UserEntity userEntityAdapter(User user) {
        PersonEntity personEntity = personAdapter.personEntityAdapter(user);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());

        return userEntity;
    }
}
