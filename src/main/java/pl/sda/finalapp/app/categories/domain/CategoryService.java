package pl.sda.finalapp.app.categories.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalapp.app.categories.api.CategoryDTO;
import pl.sda.finalapp.app.categories.api.CategoryTreeDTO;
import pl.sda.finalapp.app.categories.persistence.CategoryFromFileDTO;
import pl.sda.finalapp.app.categories.persistence.CategoryDAO;
import pl.sda.finalapp.app.categories.persistence.CategoryRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO = CategoryDAO.getInstance();
    @Autowired
    private CategoryRepository categoryRepository;

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
        final List<CategoryFromFileDTO> categoryList = categoryDAO.getCategoryList();
        CategoryFromFileDTO newCategory = CategoryFromFileDTO.applyFromCategory(categoryName);
        newCategory.setParentId(parentId);
        categoryList.add(newCategory);
    }

    public List<CategoryDTO> findAll() {
        return categoryDAO.getCategoryList().stream()
                .map(c -> c.toDTO())
                .collect(Collectors.toList());
    }

    public Optional<String> findCategoryNameById(Integer id) {
        return categoryDAO.getCategoryList()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .map(c -> c.getCategoryName());
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

    @PostConstruct
    void initializeCategories() {
        if (categoryRepository.count() == 0) {
            CategoryDAO categoryDAO = CategoryDAO.getInstance();
            Map<Integer, Integer> oldChildAndParentIdsMap = new HashMap<>();
            Map<Integer, Integer> newIdtoOldIdMap = new HashMap<>();
            final List<CategoryFromFileDTO> categoryDTOList = categoryDAO.getCategoryList();

            for (CategoryFromFileDTO dto : categoryDTOList) {
                oldChildAndParentIdsMap.put(dto.getId(), dto.getParentId());
                Category category = new Category(dto.getCategoryName());
                Category saveCategory = categoryRepository.save(category);
                newIdtoOldIdMap.put(saveCategory.getId(), dto.getId());
            }
            for (Integer newId : newIdtoOldIdMap.keySet()) {
                Category category = categoryRepository.
                        findById(newId).
                        orElseThrow(() -> new RuntimeException("Kategoria o takim id: " + newId + " nie istnieje"));
                Integer oldId = newIdtoOldIdMap.get(newId);
                Integer oldParentId = oldChildAndParentIdsMap.get(oldId);
                Integer newParentId = newIdtoOldIdMap
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue().equals(oldParentId))
                        .map(e -> e.getKey()).findFirst().orElse(null);
                category.applyParentId(newParentId);
                categoryRepository.save(category);
            }
        }
    }

}