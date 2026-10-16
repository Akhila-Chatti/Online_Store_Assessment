package online.store.controllers;

import online.store.dto.OrderDto;
import online.store.model.CheckoutRequest;
import online.store.services.CheckoutService;
import online.store.services.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CheckoutController {
    private final OrdersService ordersService;
    private final CheckoutService checkoutService;

    public CheckoutController(OrdersService ordersService, CheckoutService checkoutService) {
        this.ordersService = ordersService;
        this.checkoutService = checkoutService;
    }


    @PostMapping("/checkout")
    public String checkout(@RequestBody CheckoutRequest checkoutRequest) {
        checkoutService.checkout(checkoutRequest);
        return "success";
    }
    @GetMapping("/orders")
    public List<OrderDto> getOrders(@RequestParam String firstName, @RequestParam String lastName) {
        return ordersService.getOrdersByCustomer(firstName, lastName);
    }

}
