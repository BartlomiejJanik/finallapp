package pl.sda.finalapp.app.products;

public enum ProductType {

    BOOK("Książka"),
    PRESS("Prasa"),
    EBOOK("Ebook");

    private String plName;

    ProductType(String plName) {
        this.plName = plName;
    }

    public String getPlName() {
        return plName;
    }
}
