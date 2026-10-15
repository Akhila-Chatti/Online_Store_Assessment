package online.store.services;

import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import online.store.model.Product;
/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * Integrates with the database API and handles products and categories business logic
 */
@Service
public class ProductsService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductsService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<String> getAllSupportedCategories() {
      return productCategoryRepository.findAll()
            .stream()
            .map(productCategory -> productCategory.getCategory())
            .collect(Collectors.toList());
    }  
    public List<Product> getDealsOfTheDay(int atMostNumberOfProducts) {
        return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts);
    }
    public List<Product> getProductByCategory(String productCategory){
        return  productRepository.findByCategory(productCategory);
    }
    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }

    public Product getProductById(long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("Product with id %s doesn't exist", id)));
    }

    public List<Product> filterProducts(String category, Double minPrice, Double maxPrice, String description) {
        List<Product> products = getAllProducts();

        return products.stream()
                .filter(p -> category == null || category.trim().isEmpty() ||
                        (p.getCategory() != null && p.getCategory().equalsIgnoreCase(category)))
                .filter(p -> minPrice == null || p.getPriceUSD() >= minPrice)
                .filter(p -> maxPrice == null || p.getPriceUSD() <= maxPrice)
                .filter(p -> description == null || description.trim().isEmpty() ||
                        (p.getDescription() != null && p.getDescription().toLowerCase().contains(description.toLowerCase())))
                .collect(Collectors.toList());
    }


}
