package online.store.services;

import online.store.dto.OrderDto;
import online.store.exceptions.RequestValidationException;
import online.store.mappers.OrderMapper;
import online.store.model.Order;
import online.store.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final long maxNumberOfItems;

    public OrdersService(OrderRepository orderRepository, OrderMapper orderMapper,
                         @Value("${products.service.max-number-of-items:25}") long maxNumberOfItems) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.maxNumberOfItems = maxNumberOfItems;
    }

    @Transactional
    public void placeOrders(List<Order> orders) {
        validateNumberOfItemsOrdered(orders);
        orderRepository.saveAll(orders);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByCustomer(String firstName, String lastName) {
        return orderMapper.toDtoList(orderRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    private void validateNumberOfItemsOrdered(Iterable<Order> orders) {
        long totalNumberOfItems = 0;
        for (Order order : orders) {
            totalNumberOfItems += order.getQuantity();
        }
        if (totalNumberOfItems > maxNumberOfItems) {
            throw new RequestValidationException(
                    String.format("Number of products %d exceeded the limit of %d", totalNumberOfItems,
                            maxNumberOfItems), HttpStatus.BAD_REQUEST);
        }
    }
}
