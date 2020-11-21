package pl.sda.finalapp.app.categories.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalapp.app.categories.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
