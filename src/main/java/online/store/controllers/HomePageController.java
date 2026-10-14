package online.store.controllers;

import java.util.stream.Collectors;



import online.store.model.wrappers.ProductsWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import online.store.services.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController{
    private final ProductsService productService;
 
    public HomePageController(ProductsService productService) {
        this.productService = productService;
    }


   @GetMapping("/categories")
   public String getProductCategories() {
    return productService.getAllSupportedCategories()
            .stream()
            .collect(Collectors.joining(","));
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductsWrapper getDealsOfTheDay(@PathVariable(name="number_of_products")int numberOfDays) {
     
       return new ProductsWrapper(productService.getDealsOfTheDay(numberOfDays));
    }

    @GetMapping("/products")
    public ProductsWrapper getProductByCategory(@RequestParam(name="category",required=false)String category) {
       if(category!=null && !category.isEmpty()){
          return new ProductsWrapper(productService.getProductByCategory(category));
       }
       return new ProductsWrapper(productService.getAllProducts());
    }

   


}