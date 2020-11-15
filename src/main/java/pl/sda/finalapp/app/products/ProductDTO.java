package pl.sda.finalapp.app.products;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer id;
    private String title;
    private String description;
    private String pictureUrl;
    private BigDecimal price;
    private ProductType productType;
    private Integer categoryId;

    public ProductDTO(String title, String description, String pictureUrl, BigDecimal price, ProductType productType, Integer categoryId) {

        this.title = title;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.productType = productType;
        this.categoryId = categoryId;
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
}
