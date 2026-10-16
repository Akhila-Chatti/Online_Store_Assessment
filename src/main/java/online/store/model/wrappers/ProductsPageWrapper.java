package online.store.model.wrappers;

import online.store.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductsPageWrapper {
    private List<ProductDto> products;
    private int page;
    private int limit;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;

    public ProductsPageWrapper() {}

    public ProductsPageWrapper(List<ProductDto> products, Page<?> pageInfo) {
        this.products = products;
        this.page = pageInfo.getNumber() + 1;   // back to 1-based
        this.limit = pageInfo.getSize();
        this.totalElements = pageInfo.getTotalElements();
        this.totalPages = pageInfo.getTotalPages();
        this.hasNext = pageInfo.hasNext();
    }

    public List<ProductDto> getProducts() { return products; }
    public int getPage() { return page; }
    public int getLimit() { return limit; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isHasNext() { return hasNext; }
}