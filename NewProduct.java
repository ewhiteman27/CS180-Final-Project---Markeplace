import java.io.*;
import java.util.ArrayList;

public class NewProduct {
    public NewProduct() {

    }

    public ArrayList<String> getProducts() throws IOException {
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
        File f = new File("cart.txt");
        FileWriter fw = new FileWriter(f, false);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < cart.size(); i++) {
            pw.println(cart.get(i));
        }
        pw.close();
        fw.close();
    }

    public ArrayList<String> getBuyLog() {
        return new ArrayList<String>();
    }

    public void writeBuyLog(ArrayList<String> buy) throws IOException {
        File f = new File("buyLog.txt");
        FileWriter fw = new FileWriter(f, true);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < buy.size(); i++) {
            pw.println(buy.get(i));
        }
        pw.close();
        fw.close();
    }
}
