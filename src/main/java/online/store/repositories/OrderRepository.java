package online.store.repositories;

import online.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Looks for an order in the DB that matches the first and last name of a user
     *
     * @return a list of orders for the given user
     */
    @Query("SELECT DISTINCT o FROM online.store.model.Order o " +
            "JOIN o.product p " +
            "JOIN ProductCategory pc ON pc.category = p.category " +
            "WHERE o.firstName = :firstName AND o.lastName = :lastName " +
            "ORDER BY o.id")
    List<Order> findByFirstNameAndLastName(String firstName, String lastName);
}
