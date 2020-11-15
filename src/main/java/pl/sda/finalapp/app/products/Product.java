package pl.sda.finalapp.app.products;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    //    @Id
//    private UUID uuid = UUID.randomUUID();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String title;
    private String description;
    private String pictureUrl;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private Integer categoryId;

    public static Product fromDTO(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.title = productDTO.getTitle();
        newProduct.description = productDTO.getDescription();
        newProduct.pictureUrl = productDTO.getPictureUrl();
        newProduct.price = productDTO.getPrice();
        newProduct.productType = productDTO.getProductType();
        newProduct.categoryId = productDTO.getCategoryId();

        return newProduct;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductListDTO toListDTO(String categoryNameWithId) {
        return new ProductListDTO(
                id,
                title,
                description,
                pictureUrl,
                price,
                productType,
                categoryNameWithId);
    }

    public ProductDTO toDTO() {
        return new ProductDTO(
                id,
                title,
                description,
                pictureUrl,
                price,
                productType,
                categoryId);
    }

    public void apply(ProductDTO productDTO) {
        this.title = productDTO.getTitle();
        this.description = productDTO.getDescription();
        this.pictureUrl = productDTO.getPictureUrl();
        this.price = productDTO.getPrice();
        this.productType = productDTO.getProductType();
        this.categoryId = productDTO.getCategoryId();
    }
}