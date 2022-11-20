import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

/**
 * A Buyer class
 * Contains information about the buyer and
 * contains the methods used to process the
 * actions of the buyers
 * @ author saujinpark park1485
 *
 * @version 1.0
 */
public class Buyer extends Product {

    private ArrayList<String> cart; //Shopping cart
    private ArrayList<String> purchaseHistory; // purchase history
    private String username;
    private int total;
    private ArrayList<String> dashboard;

    public Buyer(String productName, String storeName, String productDescription, int productQuantity, double productPrice, String username) { //constructor
        super(productName, storeName, productDescription, productQuantity, productPrice);
        this.cart = cart;
        this.purchaseHistory = purchaseHistory;
        this.username = username;

    }

    public Buyer(String username) {
        super();
        this.username = username;
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public String addToCart(String sellerName, String storeName, String product, String price, String quantity) throws IOException {
        String box =  sellerName + "," + storeName + "," + product + "," + price + "," + quantity;
        File offCart = new File(username + "cart.txt");
        offCart.createNewFile();
        PrintWriter pw = new PrintWriter(offCart); //This is the cart that is available offline so when users quit the cart saves
        pw.println(box); //adds the product to the file
        pw.flush();
        pw.close();
        //updating the cart that works while the user is logged in
        BufferedReader br = new BufferedReader(new FileReader(offCart));
        String line = br.readLine();
        while (line != null) {
            cart.add(line);
            line = br.readLine();
        }
        br.close();
        return box;
    }

    public void removeFromCart(String item) throws IOException {
        ArrayList<String> temp = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(username + "cart.txt"));
        String line = br.readLine();
        while (line != null) {
            temp.add(line);
            line = br.readLine();      // The file reader is to update the offline cart
        }
        br.close();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).contains(item)) {
                temp.remove(i);
            }
        }
        PrintWriter pw = new PrintWriter(username + "cart.txt");
        for (int i = 0; i < temp.size(); i++) {
            pw.println(temp.get(i));
            pw.flush();
        }
        pw.close();
    }

    public void purchase() throws IOException {
        purchaseHistory.addAll(cart);
        File f = new File(username + "history.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(f));
        for (int i = 0; i < purchaseHistory.size(); i++) {
            pw.println(purchaseHistory.get(i));
        }
        pw.flush();
        pw.close();
        cart.clear();
    }

    //verifies that the card number was inputted correctly
    public void verifyCard(String cardNumber) throws InvalidCardException {
        String[] cardParts = new String[0];
        if (cardNumber.length() != 16) {
            throw new InvalidCardException("Invalid card number!");
        }
        cardParts = cardNumber.split(" ", 5);
        if (cardParts.length != 4) {
            throw new InvalidCardException("Invalid card number!");
        }

        boolean checkInt;
        try {
            for (int i = 0; i < cardParts.length; i++) {
                Integer.parseInt(cardParts[i]);
            }
            checkInt = true;
        } catch (final NumberFormatException e) {
            checkInt = false;
        }
        if (!checkInt) {
            throw new InvalidCardException("Invalid card number!");
        }
    }

    public ArrayList<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void exportHistory(String pathname) throws IOException {  //creates a file and writes the purchase history
        File history = new File(pathname);
        PrintWriter pw = new PrintWriter(history);
        for (int i = 0; i < purchaseHistory.size(); i++) {
            pw.println(purchaseHistory.get(i));
            pw.flush();
        }
        pw.close();
    }
    public ArrayList<String> getAllProducts() throws IOException { //gets all products in the marketplace in a single arraylist
        //getting all the seller accounts into an arraylist
        ArrayList<String> usernames = new ArrayList<>();  //DONE
        usernames.addAll(getAccounts());
        //getting the products of each seller all into a single arraylist
        ArrayList<String> allproducts = new ArrayList<>();
        ArrayList<String> withDescription = new ArrayList<>();
        String finalLine = "";
        for (int i = 0; i < usernames.size(); i++) {
            File g = new File(usernames.get(i) + ".txt");
            BufferedReader tp = new BufferedReader(new FileReader(g));
            String linetwo = tp.readLine();
            while (linetwo != null) {
                String[] temp = linetwo.split(",");
                for (int k = 0; k < temp.length; k++) {
                    withDescription.add(temp[k]);
                }
                withDescription.remove(3);
                for (int j = 0; i < withDescription.size(); j++) {
                    finalLine = finalLine + withDescription.get(j) + ",";
                }
                allproducts.add(finalLine);
                linetwo = tp.readLine();
                    }
            tp.close();
        }

        return allproducts;
    }

    public ArrayList<String> findProduct(String search) throws IOException { //searches for products that have traits similar to the search
//getting all the seller accounts into an arraylist     //DONE
        ArrayList<String> usernames = new ArrayList<>();
        usernames.addAll(getAccounts());
        //getting the products of each seller all into a single arraylist
        ArrayList<String> allproducts = new ArrayList<>();
        ArrayList<String> withDescription = new ArrayList<>();
        String finalLine = "";
        for (int i = 0; i < usernames.size(); i++) {
            File g = new File(usernames.get(i) + ".txt");
            BufferedReader tp = new BufferedReader(new FileReader(g));
            String linetwo = tp.readLine();
            while (linetwo != null) {
                String[] temp = linetwo.split(",");
                for (int k = 0; k < temp.length; k++) {
                    withDescription.add(temp[k]);
                }
                withDescription.remove(3);
                for (int j = 0; i < withDescription.size(); j++) {
                    finalLine = finalLine + withDescription.get(j) + ",";
                }
                allproducts.add(finalLine);
                linetwo = tp.readLine();
            }
            tp.close();
        }

        //sorting the all products arraylist to contain things relevant to the search
        ArrayList<String> searchResults = new ArrayList<>();
        for (int i = 0; i < allproducts.size(); i++) {
            if (allproducts.get(i).contains(search)) {
                searchResults.add(allproducts.get(i));
            }
        }
        return searchResults;
    }

    private ArrayList<String> getAccounts() {
        /**
         * reads the accounts.txt file and putting the usernames of the sellers
         * in an arraylist
         */

        File f = new File("accounts.txt");
        ArrayList<String> accounts = new ArrayList<String>();
        try {
            f.createNewFile();
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String[] account = line.split(";");
                if (account.length == 4) { // checks if the current line is an account with a valid format
                    if (Integer.parseInt(account[3]) == 2) {
                        accounts.add(line.split(";")[0]);
                    }
                }
                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    public ArrayList<String> sortPrice() throws IOException {
        //getting all the seller accounts into an arraylist
        ArrayList<String> usernames = new ArrayList<>();  //DONE
        usernames.addAll(getAccounts());
        //getting the products of each seller all into a single arraylist
        ArrayList<String> allproducts = new ArrayList<>();
        ArrayList<String> withDescription = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        String finalLine = "";
        ArrayList<String> sorted = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            File g = new File(usernames.get(i) + ".txt");
            BufferedReader tp = new BufferedReader(new FileReader(g));
            String linetwo = tp.readLine();
            while (linetwo != null) {
                String[] temp = linetwo.split(",");
                for (int k = 0; k < temp.length; k++) {
                    withDescription.add(temp[k]);
                }
                withDescription.remove(3);
                for (int j = 0; i < withDescription.size(); j++) {
                    finalLine = finalLine + withDescription.get(j) + ",";
                }
                allproducts.add(finalLine);
                linetwo = tp.readLine();
            }
            tp.close();
        }

        for (int i = 0; i < allproducts.size(); i++) {
            String[] splitContent = allproducts.get(i).split("\\$");
            prices.add(Double.parseDouble(splitContent[1]));
        }
        Collections.sort(prices);

        for (int i = 0; i < prices.size(); i++) {
            for (int j = 0; i < allproducts.size(); j++) {
                if (allproducts.get(j).contains(Double.toString(prices.get(i)))) {
                    sorted.add(allproducts.get(j));
                }
            }
        }
        Set<String> set = new LinkedHashSet<>();
        set.addAll(sorted);
        sorted.clear();
        sorted.addAll(set);

        return sorted;
    }

    public ArrayList<String> sortQuantity() throws IOException {
        //getting all the seller accounts into an arraylist
        ArrayList<String> usernames = new ArrayList<>();  //DONE
        usernames.addAll(getAccounts());
        //getting the products of each seller all into a single arraylist
        ArrayList<String> allproducts = new ArrayList<>();
        ArrayList<String> withDescription = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        String finalLine = "";
        ArrayList<String> sorted = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            File g = new File(usernames.get(i) + ".txt");
            BufferedReader tp = new BufferedReader(new FileReader(g));
            String linetwo = tp.readLine();
            while (linetwo != null) {
                String[] temp = linetwo.split(",");
                for (int k = 0; k < temp.length; k++) {
                    withDescription.add(temp[k]);
                }
                withDescription.remove(3);
                for (int j = 0; i < withDescription.size(); j++) {
                    finalLine = finalLine + withDescription.get(j) + ",";
                }
                allproducts.add(finalLine);
                linetwo = tp.readLine();
            }
            tp.close();
        }

        for (int i = 0; i < allproducts.size(); i++) {
            String[] splitContent = allproducts.get(i).split(",", 5);
            quantities.add(Integer.parseInt(splitContent[4]));
        }
        Collections.sort(quantities);

        for (int i = 0; i < quantities.size(); i++) {
            for (int j = 0; i < allproducts.size(); j++) {
                if (allproducts.get(j).contains(Character.toString(quantities.get(i)))) {
                    sorted.add(allproducts.get(j));
                }
            }
        }
        Set<String> set = new LinkedHashSet<>();
        set.addAll(sorted);
        sorted.clear();
        sorted.addAll(set);

        return sorted;
    }

    //DONE
    public ArrayList<String> getDetails(String sellerName, String storeName, String productName) throws IOException { //DONE

        //getting all the seller accounts into an arraylist
        ArrayList<String> usernames = new ArrayList<>();
        usernames.addAll(getAccounts());
        //getting the products of each seller all into a single arraylist
        ArrayList<String> allproducts = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            File g = new File(usernames.get(i) + ".txt");
            BufferedReader tp = new BufferedReader(new FileReader(g));
            String linetwo = tp.readLine();
            while (linetwo != null) {
                allproducts.add(linetwo);
                linetwo = tp.readLine();
            }
            tp.close();
        }
        //sorting the all products arraylist with the description and finding the one that fits search
        ArrayList<String> searchResults = new ArrayList<>();
        for (int i = 0; i < allproducts.size(); i++) {
            if (allproducts.get(i).contains(sellerName) && allproducts.get(i).contains(storeName) && allproducts.get(i).contains(productName)) {
                searchResults.add(allproducts.get(i));
            }
        }
        return searchResults;
    }

}

