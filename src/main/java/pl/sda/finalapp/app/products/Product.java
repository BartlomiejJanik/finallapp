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

}