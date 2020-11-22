package pl.sda.finalapp.app.categories.api;

public class CategoryState {

    private boolean selected;
    private boolean opened;

    public CategoryState(boolean selected, boolean opened) {
        this.selected = selected;
        this.opened = opened;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isOpened() {
        return opened;
    }

}