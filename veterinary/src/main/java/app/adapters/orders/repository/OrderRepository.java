package app.adapters.orders.repository;

import app.adapters.orders.entity.OrderEntity;
import app.adapters.persons.entity.PersonEntity;
import app.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOwner(PersonEntity personEntity);
}
