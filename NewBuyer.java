import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

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

    public boolean removeFromCart(String storeName, String productName) throws IOException {
        ArrayList<String> products = getProducts();
        ArrayList<String> cart = getCart();
        for (int i = 0; i < cart.size(); i++) {
            String[] product = cart.get(i).split(",");
            if (product[1].equalsIgnoreCase(storeName) && product[2].equalsIgnoreCase(productName)
                    && product[6].equalsIgnoreCase(this.username)) {
                cart.remove(i);
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
        int size = cart.size();
        for (int i = 0; i < size; i++) {
            String[] productInCart = cart.get(i).split(",");
            if (productInCart[6].equalsIgnoreCase(username)) {
                for (int j = 0; j < products.size(); j++) {
                    String[] currentProduct = products.get(j).split(",");
                    if (productInCart[0].equalsIgnoreCase(currentProduct[0])
                            && productInCart[1].equalsIgnoreCase(currentProduct[1])
                            && productInCart[2].equalsIgnoreCase(currentProduct[2])) {
                        int quantityRemove = Integer.parseInt(currentProduct[4]) - Integer.parseInt(productInCart[4]);
                        currentProduct[4] = String.valueOf(quantityRemove);
                        products.set(j, String.format("%s,%s,%s,%s,%s,%s", currentProduct[0], currentProduct[1],
                                currentProduct[2], currentProduct[3], currentProduct[4], currentProduct[5]));
                        buy.add(cart.get(i));
                        cart.remove(i);
                    }
                }
            }
            size = cart.size();
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

    public String getFormattedProduct(String productName, String storeName) throws IOException {
        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (product[1].equalsIgnoreCase(storeName) && product[2].equalsIgnoreCase(productName)) {
                return String.format("Product Name: %s;Store Name: %s;Seller: %s;Product " +
                                "Description: %s;Quantity Available: %s;Price: %s", product[2], product[1],
                        product[0], product[3], product[4], product[5]);
            }
        }
        return "Product not found";
    }

    public boolean exportFile(String pathname) throws IOException {
        ArrayList<String> buyLog = getBuyLog();
        File f = new File(pathname);
        if (!f.exists()) {
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < buyLog.size(); i++) {
                if (buyLog.get(i).split(",")[6].equalsIgnoreCase(username)) {
                    pw.println(buyLog.get(i));
                }
            }
            pw.close();
            fw.close();
            return true;
        }
        return false;
    }

    public int numInCart() throws IOException {
        ArrayList<String> cart = getCart();
        int count = 0;
        for (int i = 0; i < cart.size(); i++) {
            String[] item = cart.get(i).split(",");
            if (item[6].equalsIgnoreCase(username)) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<String> searchProduct(String searchTerm) throws IOException {
        ArrayList<String> products = getProducts();
        ArrayList<String> searchResults = new ArrayList<>();
        String search = searchTerm.toLowerCase();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).toLowerCase().contains(search)) {
                searchResults.add(products.get(i));
            }
        }
        return formatProducts(searchResults);
    }

    public ArrayList<String> sortQuantity() throws IOException, NumberFormatException {
        ArrayList<String> products = getProducts();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<String> sortedProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            quantities.add(Integer.parseInt(product[4]));
        }
        quantities.sort(Comparator.naturalOrder());
        for (int i = 0; i < quantities.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                int quantity = Integer.parseInt(products.get(j).split(",")[4]);
                if (quantity == quantities.get(i)) {
                    sortedProducts.add(products.get(j));
                    products.remove(j);
                }
            }
        }
        return formatProducts(sortedProducts);
    }

    public ArrayList<String> sortPrice() throws IOException, NumberFormatException {
        ArrayList<String> products = getProducts();
        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<String> sortedProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            prices.add(Double.parseDouble(product[5]));
        }
        prices.sort(Comparator.naturalOrder());
        for (int i = 0; i < prices.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                double price = Double.parseDouble(products.get(j).split(",")[5]);
                if (Math.abs(prices.get(i) - price) < 0.001) {
                    sortedProducts.add(products.get(j));
                    products.remove(j);
                }
            }
        }
        return sortedProducts;
    }

    public ArrayList<String> getBuyerCart() throws IOException {
        ArrayList<String> cart = getCart();
        ArrayList<String> buyerCart = new ArrayList<>();
        for (int i = 0; i < cart.size(); i++) {
            String[] item = cart.get(i).split(",");
            if (item[6].equalsIgnoreCase(username)) {
                buyerCart.add(cart.get(i));
            }
        }
        return buyerCart;
    }
     public void reviewProducts(String storeName, String productName, String writtenReview) throws IOException {
        File f = new File("Reviews.txt");
        ArrayList<String> totalReviews = new ArrayList<String>();
        f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        FileWriter fw = new FileWriter(f , false);
        PrintWriter pw = new PrintWriter(fw);
        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] productInCart = products.get(i).split(",");
            if (productInCart[1].equalsIgnoreCase(storeName) && productInCart[2].equalsIgnoreCase(productName)) {
                String reviewLine = String.format("%s,%s,%s,%s", storeName, productName, username, writtenReview);
                totalReviews.add(reviewLine);
                pw.println(reviewLine);
            }
        }
        pw.close();
        fw.close();
    }
    
    public ArrayList<String> reviewForSpecificProduct(String storeName, String productName) throws IOException {
        File f = new File("Reviews.txt");
        ArrayList<String> reviews = new ArrayList<String>();
        f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] splitTheReviews = line.split(","); 
            if (splitTheReviews.length == 4 && splitTheReviews[0].equalsIgnoreCase(storeName) && 
                    splitTheReviews[1].equalsIgnoreCase(productName)) {
                String formattedReview = String.format("%s - %s", username, splitTheReviews[3]);
                reviews.add(formattedReview);
            }
            line = bfr.readLine();
        }
        bfr.close();
        fr.close();
        return reviews;
    }
}
