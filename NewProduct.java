import java.io.*;
import java.util.ArrayList;

/**
 * Project 5 - NewProduct
 * An updated Product class and superclass to NewSeller and NewBuyer.
 * Contains methods for file input and output and product reformatting
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 12/10/22
 */

public class NewProduct {
    public NewProduct() {

    }

    public ArrayList<String> getProducts() throws IOException {
        /**
         * Reads through the products.txt file and adds all valid products to an
         * ArrayList, then returns the resultant ArrayList of product strings
         */

        File f = new File("products.txt");
        ArrayList<String> products = new ArrayList<String>();
        f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            if (line.split(",").length == 6) {
                products.add(line);
            }
            line = bfr.readLine();
        }
        bfr.close();
        fr.close();
        return products;
    }

    public void writeNewProduct(ArrayList<String> products, String user, String storeName, String productName,
                                String description, int quantity, double price) throws IOException {
        /**
         * Writes a new product to the end of the products.txt file with proper formatting
         */

        File f = new File("products.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f , false);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < products.size(); i++) {
            pw.println(products.get(i));
        }
        pw.printf("%s,%s,%s,%s,%d,%.2f\n" , user , storeName , productName , description, quantity, price);
        pw.close();
        fw.close();
    }

    public void writeProduct(ArrayList<String> products) throws IOException {
        /**
         * Updates the products.txt file to reflect the most recent changes to products
         */

        File f = new File("products.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f , false);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < products.size(); i++) {
            pw.println(products.get(i));
        }
        pw.close();
        fw.close();
    }

    public ArrayList<String> getCart() throws IOException {
        /**
         * Reads through the cart.txt file and adds all valid items to an
         * ArrayList, then returns the resultant ArrayList of item strings
         */

        File f = new File("cart.txt");
        ArrayList<String> cart = new ArrayList<String>();
        f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            if (line.split(",").length == 7) {
                cart.add(line);
            }
            line = bfr.readLine();
        }
        bfr.close();
        fr.close();
        return cart;
    }

    public void writeCart(ArrayList<String> cart) throws IOException {
        /**
         * Updates the cart.txt file to reflect the most recent changes to the cart
         */

        File f = new File("cart.txt");
        FileWriter fw = new FileWriter(f, false);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < cart.size(); i++) {
            pw.println(cart.get(i));
        }
        pw.close();
        fw.close();
    }

    public ArrayList<String> getBuyLog() throws IOException {
        /**
         * Reads through the buyLog.txt file and adds all valid items to an
         * ArrayList, then returns the resultant ArrayList of item strings
         */

        File f = new File("buyLog.txt");
        ArrayList<String> buyLog = new ArrayList<String>();
        f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            if (line.split(",").length == 7) {
                buyLog.add(line);
            }
            line = bfr.readLine();
        }
        bfr.close();
        fr.close();
        return buyLog;
    }

    public void writeBuyLog(ArrayList<String> buy) throws IOException {
        /**
         * Updates the buyLog.txt file to reflect the most recent changes to the buyLog
         */

        File f = new File("buyLog.txt");
        FileWriter fw = new FileWriter(f, true);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < buy.size(); i++) {
            pw.println(buy.get(i));
        }
        pw.close();
        fw.close();
    }

    public ArrayList<String> formatProducts(ArrayList<String> products) throws IOException {
        /**
         * Returns an ArrayList of strings representing reformatted versions of
         * the items in the given ArrayList that are more readable for users
         */

        ArrayList<String> formattedProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            formattedProducts.add(String.format("Product Name: %s, Store: %s," +
                    " Price: %s", product[2], product[1], product[5]));
        }
        return formattedProducts;
    }

    public ArrayList<String> formatCartItems(ArrayList<String> cart) throws IOException {
        /**
         * Returns an ArrayList of strings representing reformatted versions of
         * the items in the given ArrayList that are more readable for users
         */

        ArrayList<String> formattedCart = new ArrayList<>();
        for (int i = 0; i < cart.size(); i++) {
            String[] product = cart.get(i).split(",");
            formattedCart.add(String.format("Buyer: %s, Product Name: %s, Store: %s," +
                    " Price: %s, Quantity: %s", product[6], product[2], product[1], product[5], product[4]));
        }
        return formattedCart;
    }
}
