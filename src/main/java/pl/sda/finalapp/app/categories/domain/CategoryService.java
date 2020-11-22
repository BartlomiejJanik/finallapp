package pl.sda.finalapp.app.categories.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalapp.app.categories.api.CategoryDTO;
import pl.sda.finalapp.app.categories.api.CategoryTreeDTO;
import pl.sda.finalapp.app.categories.persistence.CategoryFromFileDTO;
import pl.sda.finalapp.app.categories.persistence.CategoryDAO;
import pl.sda.finalapp.app.categories.persistence.CategoryRepository;
import pl.sda.finalapp.app.products.ProductService;
import pl.sda.finalapp.app.products.ProductType;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryTreeDTO> findCategories(String searchText) {
        final List<CategoryTreeDTO> dtos = categoryRepository.findAll().stream()
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
        categoryRepository.save(new Category(categoryName, parentId));
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(c -> c.toDTO())
                .collect(Collectors.toList());
    }

    public Optional<String> findCategoryNameById(Integer id) {
        return categoryRepository.findCategoryNameById(id);
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
        if (categoryRepository.count() != 0) {
            return;
        }
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        Map<Integer, Integer> oldChildAndOldParentIdsMap = new HashMap<>();
        Map<Integer, Integer> newIdToOldIdMap = new HashMap<>();
        final List<CategoryFromFileDTO> categoryDTOList = categoryDAO.getCategoryList();

        saveCategoryAndPopulateMaps(oldChildAndOldParentIdsMap, newIdToOldIdMap, categoryDTOList);
        populateParentIds(oldChildAndOldParentIdsMap, newIdToOldIdMap);
    }

    private void populateParentIds(Map<Integer, Integer> oldChildAndOldParentIdsMap, Map<Integer, Integer> newIdToOldIdMap) {
        for (Integer newId : newIdToOldIdMap.keySet()) {
            final Category category = categoryRepository.findById(newId).orElseThrow(() -> new RuntimeException("Kategoria o id " + newId + " nie znaleziona."));
            final Integer oldId = newIdToOldIdMap.get(newId);
            final Integer oldParentId = oldChildAndOldParentIdsMap.get(oldId);
            final Integer newParentId = newIdToOldIdMap.entrySet().stream()
                    .filter(e -> e.getValue().equals(oldParentId))
                    .map(e -> e.getKey())
                    .findFirst()
                    .orElse(null);
            if (newParentId == null) {
                continue;
            }
            categoryRepository.save(category.applyParentId(newParentId));
        }
    }

    private void saveCategoryAndPopulateMaps(Map<Integer, Integer> oldChildAndOldParentIdsMap, Map<Integer, Integer> newIdToOldIdMap, List<CategoryFromFileDTO> categoryDTOList) {
        for (CategoryFromFileDTO dto : categoryDTOList) {
            oldChildAndOldParentIdsMap.put(dto.getId(), dto.getParentId());
            final Category category = new Category(dto.getCategoryName());
            final Category savedCategory = categoryRepository.save(category);
            newIdToOldIdMap.put(savedCategory.getId(), dto.getId());
        }
    }

    public void moveCategory(Integer newParentId, Integer movedId) {
        final Category category = categoryRepository.findById(movedId)
                .orElseThrow(() -> new RuntimeException("Kategoria o id:" + movedId + " nie została odnaleziona"))
                .applyParentId(newParentId);
        categoryRepository.save(category);
    }

}