package pl.sda.finalapp.app.categories.api;

public class CategoryTreeDTO {

    public static final String NO_PARENT_VALUE = "#";
    private Integer id;
    private Integer parentId;
    private String categoryName;
    private CategoryState state;

    public CategoryTreeDTO(Integer id, Integer parentId, String categoryName) { //DTO Data transfer object
        this.id = id;
        this.parentId = parentId;
        this.categoryName = categoryName;
    }

    public String getText (){
        return id + " " + categoryName;
    }

    public String getParent(){
        if (parentId == null){
            return NO_PARENT_VALUE;
        }
        return String.valueOf(parentId);
    }

    public Integer getId() {
        return id;
    }

    public CategoryState getState() {
        if(state == null){
            return new CategoryState(false, false);
        }
        return state;
    }

    public void setSelected(boolean selected){
        if(state == null){
            state = new CategoryState(selected, false);
        } else {
            state = new CategoryState(selected, state.isOpened());
        }
    }

    public void setOpened(boolean opened){
        if(state == null){
            state = new CategoryState(false, opened);
        } else {
            state = new CategoryState(state.isSelected(), opened);
        }
    }
}