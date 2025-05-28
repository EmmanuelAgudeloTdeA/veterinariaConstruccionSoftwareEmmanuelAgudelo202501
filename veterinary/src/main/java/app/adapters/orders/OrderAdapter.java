package app.adapters.orders;

import app.adapters.orders.entity.OrderEntity;
import app.adapters.orders.repository.OrderRepository;
import app.adapters.persons.PersonAdapter;
import app.adapters.persons.entity.PersonEntity;
import app.adapters.users.UserAdapter;
import app.adapters.users.entity.UserEntity;
import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.User;
import app.ports.OrderPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
@NoArgsConstructor
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PersonAdapter personAdapter;
    @Autowired
    private UserAdapter userAdapter;

    @Override
    public void saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityAdapter(order);
        orderRepository.save(orderEntity);
        order.setOrderId(orderEntity.getOrderId());
    }

    @Override
    public Order findByOwner(Person owner) {
        PersonEntity personEntity = personAdapter.personEntityAdapter(owner);
        OrderEntity orderEntity = orderRepository.findByOwner(personEntity);
        if (orderEntity == null) {
            return null;
        }
        return orderAdapter(orderEntity);
    }

    @Override
    public void annularOrder(Order order) {

    }

    public Order orderAdapter(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        Order order = new Order();
        order.setOrderId(orderEntity.getOrderId());
        order.setMedicationName(orderEntity.getMedicationName());
        order.setMedicationDosage(orderEntity.getMedicationDosage());
        order.setDate(orderEntity.getDate());
        if (orderEntity.getOwner() != null) {
            Person owner = personAdapter.personAdapter(orderEntity.getOwner());
            order.setOwner(owner);
        }
        if (orderEntity.getVeterinarian() != null) {
            User veterinarian = userAdapter.userAdapter(orderEntity.getVeterinarian());
            order.setVeterinarian(veterinarian);
        }
        return order;
    }

    public OrderEntity orderEntityAdapter(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setMedicationName(order.getMedicationName());
        orderEntity.setMedicationDosage(order.getMedicationDosage());
        orderEntity.setDate(order.getDate());
        if (order.getOwner() != null) {
            PersonEntity ownerEntity = personAdapter.personEntityAdapter(order.getOwner());
            orderEntity.setOwner(ownerEntity);
        }
        if (order.getVeterinarian() != null) {
            UserEntity veterinarianEntity = userAdapter.userEntityAdapter(order.getVeterinarian());
            orderEntity.setVeterinarian(veterinarianEntity);
        }
        return orderEntity;
    }
}
