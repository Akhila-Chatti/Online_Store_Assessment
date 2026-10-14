package online.store.model.wrappers;

import online.store.model.Product;
import java.util.List;
import java.util.Collections;

public class ProductsWrapper {
    private List<Product> products = Collections.EMPTY_LIST;
 
    public ProductsWrapper(List<Product> products) {
        this.products = Collections.unmodifiableList(products);
    }
 
    public List<Product> getProducts() {
        return products;
    }
}