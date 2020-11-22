package pl.sda.finalapp.app.products;

import java.math.BigDecimal;

public class ProductListDTO {

    private Integer id;
    private String title;
    private String description;
    private String pictureUrl;
    private BigDecimal price;
    private ProductType productType;
    private String category;

    public ProductListDTO(Integer id, String title, String description, String pictureUrl, BigDecimal price, ProductType productType, String categoryNameWithId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.productType = productType;
        this.category = categoryNameWithId;
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

    public String getCategory() {
        return category;
    }
}