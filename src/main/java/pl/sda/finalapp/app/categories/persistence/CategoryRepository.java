package pl.sda.finalapp.app.categories.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sda.finalapp.app.categories.domain.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c.categoryName FROM Category c WHERE c.id=?1")//c.id=?1 -- pierwszy elemnt z konsturktora
    Optional<String> findCategoryNameById(Integer id);
}