package pl.sda.finalapp.app.products;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer id;
    private String title;
    private String description;
    private String pictureUrl;
    private BigDecimal price;
    private ProductType productType;
    private Integer categoryId;

    public ProductDTO() {
    }

    public ProductDTO(String title, String description, String pictureUrl, BigDecimal price, ProductType productType, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.productType = productType;
        this.categoryId = categoryId;
    }

    public ProductDTO(Integer id, String title, String description, String pictureUrl, BigDecimal price, ProductType productType, Integer categoryId) {
        this(title, description, pictureUrl, price, productType, categoryId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}