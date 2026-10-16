package online.store.services;

import online.store.dto.ProductDto;
import online.store.exceptions.RequestValidationException;
import online.store.exceptions.ResourceNotFoundException;
import online.store.mappers.ProductMapper;
import online.store.model.Product;
import online.store.model.wrappers.ProductsPageWrapper;
import online.store.model.wrappers.ProductsWrapper;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private static final int MAX_PAGE_SIZE = 100;

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;

    public ProductsService(ProductRepository productRepository,
                           ProductCategoryRepository productCategoryRepository,
                           ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMapper = productMapper;
    }

    public String getAllSupportedCategories() {
        return productCategoryRepository.findAll().stream()
                .map(category -> category.getCategory())
                .collect(Collectors.joining(","));
    }

    public ProductsWrapper getDealsOfTheDay(int numberOfProducts) {
        if (numberOfProducts < 1) {
            throw new RequestValidationException("number_of_products must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        List<ProductDto> products = productMapper.toDtoList(
                productRepository.findAtMostNumberOfProducts(numberOfProducts));
        return new ProductsWrapper(products);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " was not found"));
    }

    public ProductsPageWrapper getProductsPage(String category, int page, int limit) {
        Pageable pageable = toPageable(page, limit);
        Page<Product> products = isBlank(category)
                ? productRepository.findAll(pageable)
                : productRepository.findByCategory(category.trim(), pageable);
        return toPageWrapper(products);
    }

    public ProductsPageWrapper filterProductsPage(String category, Float minPrice, Float maxPrice,
                                                    String description, int page, int limit) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new RequestValidationException("min_price must not exceed max_price", HttpStatus.BAD_REQUEST);
        }
        String normalizedCategory = isBlank(category) ? null : category.trim();
        String normalizedDescription = isBlank(description) ? null : description.trim();
        Page<Product> products = productRepository.filter(normalizedCategory, minPrice, maxPrice,
                normalizedDescription, toPageable(page, limit));
        return toPageWrapper(products);
    }

    private ProductsPageWrapper toPageWrapper(Page<Product> page) {
        return new ProductsPageWrapper(productMapper.toDtoList(page.getContent()), page);
    }

    private Pageable toPageable(int page, int limit) {
        int safePage = Math.max(page, 1);
        int safeLimit = Math.min(Math.max(limit, 1), MAX_PAGE_SIZE);
        return PageRequest.of(safePage - 1, safeLimit, Sort.by("id"));
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
