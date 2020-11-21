package pl.sda.finalapp.app.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE (p.title LIKE CONCAT('%',?1,'%') " +
            "OR p.description LIKE CONCAT('%',?1,'%')) " +
            "AND p.productType = ?2 " +
            "AND p.categoryId = ?3")
    List<Product> findProducts(String searchText, ProductType productType, Integer categoryId);
}