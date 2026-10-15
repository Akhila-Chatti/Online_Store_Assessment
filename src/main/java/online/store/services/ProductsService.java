package online.store.services;

import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import online.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Product> filterProducts(String category, Float minPrice, Float maxPrice, String description) {
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

    private static final int MAX_LIMIT = 100;

    private Pageable toPageable(int page, int limit) {
        int safePage = Math.max(page, 1);
        int safeLimit = Math.min(Math.max(limit, 1), MAX_LIMIT);
        return PageRequest.of(safePage - 1, safeLimit, Sort.by("id"));
    }

    public Page<Product> getProductsPage(String category, int page, int limit) {
        Pageable pageable = toPageable(page, limit);
        if (category == null || category.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findByCategory(category.trim(), pageable);
    }

    public Page<Product> filterProductsPage(String category, Float minPrice, Float maxPrice,
                                            String description, int page, int limit) {
        String cat = (category == null || category.trim().isEmpty()) ? null : category.trim();
        String desc = (description == null || description.trim().isEmpty()) ? null : description.trim();
        return productRepository.filter(cat, minPrice, maxPrice, desc, toPageable(page, limit));
    }
}
