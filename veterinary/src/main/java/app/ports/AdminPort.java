package app.ports;

import app.domain.models.Person;

public interface AdminPort {
    public Person findByUserId(long id);
}
