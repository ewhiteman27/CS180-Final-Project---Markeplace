import java.util.ArrayList;

public class Product {
    private String productName;
    private String storeName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;

    private ArrayList<String> reviews;

    public Product(String productName, String storeName, String productDescription, int productQuantity, double productPrice) {
        this.productName = productName;
        this.storeName = storeName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.reviews = reviews;
    }

    public Product() {
        this.productName = null;
        this.storeName = null;
        this.productDescription = null;
        this.productQuantity = 0;
        this.productPrice = 0.0;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    public int getProductQuantity() {
        return productQuantity;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }
    public ArrayList<String> getReviews() {
        return reviews;
    }
    public String toString() {
        return String.format("Store Name: %s,Name of Product: %s,Description of Product: %s" +
                        ",Quantity Available of Product: %d,Price of product: $%.2f",
                getStoreName(), getProductName(), getProductDescription(), getProductQuantity(), getProductPrice());
    }
}
