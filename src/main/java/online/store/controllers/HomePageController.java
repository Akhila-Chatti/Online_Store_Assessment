package online.store.controllers;

import java.util.List;
import java.util.stream.Collectors;


import online.store.dto.OrderDto;
import online.store.mappers.OrderMapper;
import online.store.mappers.ProductMapper;
import online.store.model.wrappers.ProductsWrapper;
import online.store.services.OrdersService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import online.store.services.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController{
    private final ProductsService productService;
    private final ProductMapper productMapper;
 
    public HomePageController(ProductsService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;

    }


   @GetMapping("/categories")
   public String getProductCategories() {
    return productService.getAllSupportedCategories()
            .stream()
            .collect(Collectors.joining(","));
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductsWrapper getDealsOfTheDay(@PathVariable(name="number_of_products")int numberOfProducts) {
     
       return new ProductsWrapper(productMapper.toDtoList(productService.getDealsOfTheDay(numberOfProducts)));
    }

    @GetMapping("/products")
    public ProductsWrapper getProductByCategory(@RequestParam(name="category",required=false)String category) {
       if(category!=null && !category.isEmpty()){
          return new ProductsWrapper(productMapper.toDtoList(productService.getProductByCategory(category)));
       }
       return new ProductsWrapper(productMapper.toDtoList(productService.getAllProducts()));
    }
    @GetMapping("/products/filter")
    public ProductsWrapper filterProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @RequestParam(name = "description", required = false) String description) {

        // Pass the parameters down to your service layer to handle filtering logic
        return new ProductsWrapper(productMapper.toDtoList(
                productService.filterProducts(category, minPrice, maxPrice, description)));
    }




   


}