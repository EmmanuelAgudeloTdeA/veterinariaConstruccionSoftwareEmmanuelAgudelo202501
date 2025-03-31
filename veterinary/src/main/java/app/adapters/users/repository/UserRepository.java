package app.adapters.users.repository;

import app.adapters.persons.entity.PersonEntity;
import app.adapters.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public boolean existsByUserName(String userName);

    public UserEntity findByPerson(PersonEntity personEntity);

    public UserEntity findByUserName(String userName);
}
