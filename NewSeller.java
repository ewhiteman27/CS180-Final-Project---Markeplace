import java.io.*;
import java.util.ArrayList;

/**
 * Project 5 - NewSeller
 * An updated Seller class including methods for product creation, management, and deletion,
 * as well as statistics for items that have been purchased or are in customer carts
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 12/10/22
 */

public class NewSeller extends NewProduct {
    String username;

    public NewSeller(String username) {
        super();
        this.username = username;
    }

    public boolean createProduct(String storeName, String productName, String description,
                                 int quantity, double price) throws IOException {
        /**
         * Creates a new product if no product already exists with a matching name, store, and seller. Writes
         * the new product to the products.txt file by calling the writeNewProduct method in the NewProduct class.
         * Returns true if the product was successfully created and false otherwise
         */

        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (storeName.equalsIgnoreCase(product[1]) && productName.equalsIgnoreCase(product[2])) {
                return false;
            }
        }
        writeNewProduct(products, username, storeName, productName, description, quantity, price);
        return true;
    }

    public boolean editProduct(String storeName, String productName, String newStoreName, String newProductName,
                               String newDescription, int newQuantity, double newPrice) throws IOException {
        /**
         * Edits the product specified by the storeName and productName parameters to have all other
         * given parameters. Writes the updated product to the products.txt file on the same line as
         * the existing product it is replacing by calling the writeProduct method in the NewProduct class.
         * Returns true if the product is successfully updated and false otherwise
         */

        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (username.equalsIgnoreCase(product[0]) && storeName.equalsIgnoreCase(product[1])
                    && productName.equalsIgnoreCase(product[2])) {
                products.set(i, String.format("%s,%s,%s,%s,%d,%.2f", username, newStoreName, newProductName,
                        newDescription, newQuantity, newPrice));
                writeProduct(products);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String storeName, String productName) throws IOException {
        /**
         * Finds the product that matches this seller's username and the given store name and product
         * name and removes it from the products arrayList, then writes the updated list of products
         * to the products.txt file by calling the writeProduct method in the NewProduct class. Returns
         * true if the product is successfully deleted and false otherwise
         */

        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (username.equalsIgnoreCase(product[0]) && storeName.equalsIgnoreCase(product[1])
                    && productName.equalsIgnoreCase(product[2])) {
                products.remove(i);
                writeProduct(products);
                return true;
            }
        }
        return false;
    }

    public boolean importFile(String pathname) throws FileNotFoundException, IOException, NumberFormatException {
        /**
         * Returns false if the file specified does not exist. If the file exists, reads through each
         * line, checks if it contains a valid product, and if it does, adds the line to the products
         * ArrayList. After reading through the file, writes the new products to the product.txt file
         * using the writeProduct method in the NewProduct class and returns true
         */

        ArrayList<String> products = getProducts();
        File f = new File(pathname);
        if (f.exists()) {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String[] product = line.split(",");
                if (product.length == 6) {
                    products.add(line);
                }
                line = bfr.readLine();
            }
            writeProduct(products);
            bfr.close();
            fr.close();
            return true;
        }
        return false;
    }

    public boolean exportFile(String pathname) throws IOException {
        /**
         * If the given file already exists, returns false, otherwise, creates the file then
         * adds all products which are being sold by this user to the file and returns true
         */

        ArrayList<String> products = getProducts();
        File f = new File(pathname);
        if (!f.exists()) {
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).split(",")[0].equalsIgnoreCase(username)) {
                    pw.println(products.get(i));
                }
            }
            pw.close();
            fw.close();
            return true;
        }
        return false;
    }
     public ArrayList<String> sellerLog(String storeName) throws IOException {
         /**
          * Returns an ArrayList containing all items from the buyLog.txt file which are products sold by this user
          */

        ArrayList<String> buyLog = getBuyLog();
        ArrayList<String> storeBuyLog = new ArrayList<>();
        for (int i = 0; i < buyLog.size(); i++) {
            if (buyLog.get(i).split(",")[1].equalsIgnoreCase(storeName)) {
                storeBuyLog.add(buyLog.get(i));
            }
        }
        return formatCartItems(storeBuyLog);
    }

    public ArrayList<String> getSellerCart() throws IOException {
        /**
         * Returns an ArrayList containing all items from the cart.txt file which are products sold by this user
         */

        ArrayList<String> cart = getCart();
        ArrayList<String> sellerCart = new ArrayList<>();
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).split(",")[0].equalsIgnoreCase(username)) {
                sellerCart.add(cart.get(i));
            }
        }
        return formatCartItems(sellerCart);
    }
}
