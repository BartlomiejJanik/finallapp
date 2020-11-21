package pl.sda.finalapp.app.categories.persistence;

import pl.sda.finalapp.app.categories.api.CategoryDTO;
import pl.sda.finalapp.app.categories.api.CategoryTreeDTO;

/* domenowy model danych */
public class CategoryFromFileDTO {

    private static Integer idCounter = 0;
    private Integer id;
    private Integer parentId;
    private String categoryName;
    private Integer depth;

    public static CategoryFromFileDTO applyFromCategory(String t){
        CategoryFromFileDTO category = new CategoryFromFileDTO();
        category.categoryName = t.trim();
        category.id = ++idCounter;
        category.depth = calculateDepth(t);

        return category;
    }

    private static int calculateDepth(String t) {
        if(!t.startsWith(" ")){
            return 0;
        }
        return t.split("\\S+")[0].length();
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getDepth() {
        return depth;
    }

    public CategoryTreeDTO toTreeDTO(){
        return new CategoryTreeDTO(id, parentId, categoryName);
    }

    public CategoryDTO toDTO(){
        return new CategoryDTO(id, categoryName);
    }
}