import java.io.*;
import java.util.ArrayList;

public class NewBuyer extends NewProduct {
    String username;

    public NewBuyer(String username) {
        super();
        this.username = username;
    }

    public boolean addToCart(String storeName, String productName, int quantity) throws IOException {
        ArrayList<String> products = getProducts();
        ArrayList<String> cart = getCart();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (product[1].equalsIgnoreCase(storeName) && product[2].equalsIgnoreCase(productName)
                    && quantity < Integer.parseInt(product[4])) {
                String cartLine = String.format("%s,%s,%s,%s,%d,%s,%s", product[0],
                        product[1], product[2], product[3], quantity, product[5], username);
                cart.add(cartLine);
                writeCart(cart);
                return true;
            }
        }
        return false;
    }

    public boolean buy() throws IOException, NumberFormatException {
        ArrayList<String> products = getProducts();
        ArrayList<String> cart = getCart();
        ArrayList<String> buy = new ArrayList<String>();
        for (int i = 0; i < cart.size(); i++) {
            String[] productInCart = cart.get(i).split(",");
            if (productInCart[6].equalsIgnoreCase(username)) {
                buy.add(cart.get(i));
                cart.remove(i);
                for (int j = 0; j < products.size(); j++) {
                    String[] currentProduct = products.get(i).split(",");
                    if (productInCart[0].equalsIgnoreCase(currentProduct[0])
                            && productInCart[1].equalsIgnoreCase(currentProduct[1])
                            && productInCart[2].equalsIgnoreCase(currentProduct[2])) {
                        int quantityRemove = Integer.parseInt(currentProduct[4]) - Integer.parseInt(productInCart[4]);
                        currentProduct[4] = String.valueOf(quantityRemove);
                        products.set(i, String.format("%s,%s,%s,%s,%s,%s", currentProduct[0], currentProduct[1],
                                currentProduct[2], currentProduct[3], currentProduct[4], currentProduct[5]));
                    }
                }
            }
        }
        if (buy.isEmpty()) {
            return false;
        } else {
            writeCart(cart);
            writeBuyLog(buy);
            writeProduct(products);
            return true;
        }
    }
}
