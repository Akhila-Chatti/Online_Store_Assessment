package online.store.dto;

public class OrderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private long quantity;
    private ProductDto product;

    public OrderDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public ProductDto getProduct() { return product; }
    public void setProduct(ProductDto product) { this.product = product; }
}