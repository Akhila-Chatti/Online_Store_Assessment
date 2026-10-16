package online.store.model.wrappers;

import online.store.dto.ProductDto;
import java.util.List;

public class ProductsWrapper {
    private List<ProductDto> products;
 
    public ProductsWrapper(List<ProductDto> products) {
        this.products = products;
    }

    public void setProducts(List<ProductDto> products)
    { this.products = products; }
    public List<ProductDto> getProducts() {
        return products;
    }
}
