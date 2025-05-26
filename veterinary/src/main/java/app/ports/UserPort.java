package app.ports;

import app.domain.models.Person;
import app.domain.models.User;

public interface UserPort {
    public boolean existsUserName(String userName);
    public void saveUser(User user);
    public User findByPerson(Person person);
    public User findByUserName(User user);
}
