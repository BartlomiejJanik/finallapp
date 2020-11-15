package pl.sda.finalapp.app.categories.api;

public class CategoryDTO {

    private Integer id;
    private String categoryName;

    public CategoryDTO(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}