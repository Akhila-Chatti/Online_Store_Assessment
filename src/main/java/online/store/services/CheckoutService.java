package online.store.services;

import online.store.exceptions.RequestValidationException;
import online.store.model.CheckoutRequest;
import online.store.model.Order;
import online.store.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {
    private final OrdersService ordersService;
    private final ProductsService productsService;
    private final CreditCardValidationService creditCardValidationService;

    public CheckoutService(OrdersService ordersService, ProductsService productsService,
                           CreditCardValidationService creditCardValidationService) {
        this.ordersService = ordersService;
        this.productsService = productsService;
        this.creditCardValidationService = creditCardValidationService;
    }

    public void checkout(CheckoutRequest checkoutRequest) {
        if (checkoutRequest == null) {
            throw new RequestValidationException("Checkout request is required", HttpStatus.BAD_REQUEST);
        }

        List<String> errors = new ArrayList<>();

        if (isNullOrBlank(checkoutRequest.getCreditCard())) {
            errors.add("Credit card information is missing");
        }

        if (isNullOrBlank(checkoutRequest.getFirstName())) {
            errors.add("First name is missing");
        }

        if (isNullOrBlank(checkoutRequest.getLastName())) {
            errors.add("Last name is missing");
        }

        if (checkoutRequest.getProducts() == null
                || checkoutRequest.getProducts().isEmpty()) {
            errors.add("At least one product is required");
        }

        if (!errors.isEmpty()) {
            throw new RequestValidationException(
                    String.join("|", errors), HttpStatus.BAD_REQUEST);
        }

        creditCardValidationService.validate(checkoutRequest.getCreditCard());

        List<Order> orders = new ArrayList<>(checkoutRequest.getProducts().size());
        for (CheckoutRequest.ProductInfo productInfo : checkoutRequest.getProducts()) {
            if (productInfo == null || productInfo.getQuantity() <= 0) {
                throw new RequestValidationException("Product and quantity must be valid", HttpStatus.BAD_REQUEST);
            }
            Product product = productsService.getProductById(productInfo.getProductId());
            orders.add(new Order(checkoutRequest.getFirstName(), checkoutRequest.getLastName(),
                    checkoutRequest.getEmail(), checkoutRequest.getShippingAddress(), productInfo.getQuantity(),
                    product, checkoutRequest.getCreditCard()));
        }
        ordersService.placeOrders(orders);
    }

    private boolean isNullOrBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
