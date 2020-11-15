package pl.sda.finalapp.app.products;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer productId) {
        super("Nie znaleziono produktu o ID: "+productId);
    }
}