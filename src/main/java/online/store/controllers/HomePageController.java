package online.store.controllers;

import online.store.model.wrappers.ProductsPageWrapper;
import online.store.model.wrappers.ProductsWrapper;
import online.store.services.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
    private final ProductsService productsService;

    public HomePageController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/categories")
    public String getProductCategories() {
        return productsService.getAllSupportedCategories();
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductsWrapper getDealsOfTheDay(
            @PathVariable(name = "number_of_products") int numberOfProducts) {
        return productsService.getDealsOfTheDay(numberOfProducts);
    }

    @GetMapping("/products")
    public ProductsPageWrapper getProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return productsService.getProductsPage(category, page, limit);
    }

    @GetMapping("/products/filter")
    public ProductsPageWrapper filterProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "min_price", required = false) Float minPrice,
            @RequestParam(name = "max_price", required = false) Float maxPrice,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return productsService.filterProductsPage(category, minPrice, maxPrice, description, page, limit);
    }
}
