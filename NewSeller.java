import java.io.*;
import java.util.ArrayList;

public class NewSeller extends NewProduct {
    String username;

    public NewSeller(String username) {
        super();
        this.username = username;
    }

    public boolean createProduct(String storeName, String productName,
                                 String description, int quantity, double price)
            throws IOException {
        ArrayList<String> products = getProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i).split(",");
            if (username.equalsIgnoreCase(product[0]) && storeName.equalsIgnoreCase(product[1])
                    && productName.equalsIgnoreCase(product[2])) {
                return false;
            }
        }
        writeNewProduct(products, username, storeName, productName, description, quantity, price);
        return true;
    }

    public boolean editProduct(String storeName, String productName, String newStoreName, String newProductName,
                               String newDescription, int newQuantity, double newPrice) throws IOException {
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
