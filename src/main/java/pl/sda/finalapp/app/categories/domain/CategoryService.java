package pl.sda.finalapp.app.categories.domain;

import org.springframework.stereotype.Service;
import pl.sda.finalapp.app.categories.api.CategoryDTO;
import pl.sda.finalapp.app.categories.api.CategoryTreeDTO;
import pl.sda.finalapp.app.categories.persistence.Category;
import pl.sda.finalapp.app.categories.persistence.CategoryDAO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public List<CategoryTreeDTO> findCategories(String searchText) {
        final List<CategoryTreeDTO> dtos = categoryDAO.getCategoryList().stream()
                .map(c -> c.toTreeDTO())
                .collect(Collectors.toList());

        if (searchText == null || searchText.isBlank()) {
            return dtos;
        }

        dtos.stream()
                .filter(c -> c.getText().trim().toLowerCase().contains(searchText.trim().toLowerCase()))
                .forEach(c -> {
                    c.setSelected(true);
                    openAllParents(c, dtos);
                });

        return dtos;
    }

    public void addCategory(String categoryName, Integer parentId) {
        final List<Category> categoryList = categoryDAO.getCategoryList();
        Category newCategory = Category.applyFromCategory(categoryName);
        newCategory.setParentId(parentId);
        categoryList.add(newCategory);
    }

    public List<CategoryDTO> findAll() {
        return categoryDAO.getCategoryList().stream()
                .map(c -> c.toDTO())
                .collect(Collectors.toList());
    }

    private void openAllParents(CategoryTreeDTO child, List<CategoryTreeDTO> parents) {
        if (child.getParent().equals(CategoryTreeDTO.NO_PARENT_VALUE)) {
            return;
        }

        final Integer parentId = Integer.valueOf(child.getParent());

        parents.stream()
                .filter(p -> p.getId().equals(parentId))
                .findFirst()
                .map(p -> {
                    p.setOpened(true);
                    openAllParents(p, parents);
                    return p;
                });
    }
}