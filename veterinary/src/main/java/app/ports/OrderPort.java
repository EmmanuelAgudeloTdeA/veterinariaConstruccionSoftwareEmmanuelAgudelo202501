package app.ports;

import app.domain.models.Order;
import app.domain.models.Person;

public interface OrderPort {
    public void saveOrder(Order order);
    public Order findByOwner(Person owner);
    public void annularOrder(Order order);
}
