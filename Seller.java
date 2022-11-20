import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Seller extends Product{

    User username = new User();
    String user = username.getUsername();
    public Seller (String productName, String storeName, String productDescription, int productQuantity, double productPrice, String user) {
        super(productName, storeName, productDescription, productQuantity, productPrice);
        this.user = user;

    }

    public Seller(String user) { // use this class when editting main method
        super();
        this.user = user;
    }
    public Seller() {
        super();
        this.user = null;
    }
// add user credentials under every create method
    // create method that creates file to begin with

    public boolean importUserFile(String user, String filePath) {
        try {
            File f3 = new File(filePath);
            File f = new File(user + ".txt");
            f.createNewFile();
            ArrayList<String> arr1 = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(f3));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
            String line = bfr.readLine();
            while (line != null) {
                arr1.add(line);
                line = bfr.readLine();
            }
            for (int i = 0; i < arr1.size(); i++) {
                String[] arr2 = arr1.get(i).split(",");
                if (arr2.length != 4 || arr2[i] == null) {
                    return false;
                }
            }
            if (!(arr1 == null))  {
                for (int i = 0; i < arr1.size(); i++) {
                    bfw.write(arr1.get(i));
                    if (arr1.get(i + 1) != null) {
                        bfw.newLine();
                    }
                }
            }
            bfw.flush();
            bfw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void exportUserFile(String user, String filepath) {
        try {
            File f = new File(filepath);
            File f2 = new File(user + ".txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f2));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
            String line = bfr.readLine();
            String data = "";
            while (line != null) {
                data += line;
                line = bfr.readLine();
                if (line != null) {
                    data += "\n";
                }
            }
            bfw.write(data);
            bfw.flush();
            bfw.close();
        } catch (Exception e) {
            return;
        }
    }
    /*public void createNewUserFile(String user) {
        try {
            File f = new File(user + ".txt");
            f.createNewFile();
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
            bfw.write(user);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (Exception e) {
            return;
        }
    }*/
    /*public void addStoreName(String user, String storeName) {
        try {
            File f = new File(user + ".txt");
            f.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
            String line = bfr.readLine();
            String previousData = "";
            while (line != null) {
                previousData += line + "\n";
                line = bfr.readLine();
            }
            previousData += "\n" + storeName;
            bfw.write(previousData);
            bfw.flush();
            bfw.close();
        } catch (Exception e) {
            return;
        }
    }*/
    public boolean createProduct(String productName, String storeName, String productDescription, int productQuantity, double productPrice, String user) {
        Product product1 = new Product(productName,storeName,productDescription,productQuantity,productPrice);
        Seller seller1 = new Seller(productName, storeName, productDescription, productQuantity, productPrice, user);
        String data = "";
        try {
            File f = new File(user + ".txt");
            f.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f, true));
            String line = bfr.readLine();
            while (line != null) {
                data += line;
                line = bfr.readLine();
                if (line != null) {
                    data += "\n";
                }
            }
            /*if (data != null || data.equalsIgnoreCase("")) {
                bfw.write(data);
                bfw.newLine();
            }*/
            bfw.write(seller1.toString());
            bfw.newLine();
            bfw.flush();
            bfw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editProduct(String productName, String storeName, String productDescription, int productQuantity, double productPrice, String user)  {
        Product product2 = new Product(productName,storeName,productDescription,productQuantity,productPrice);
        Seller seller2 = new Seller(productName,storeName,productDescription,productQuantity,productPrice,user);
        ArrayList<String> data = new ArrayList<>();
        try {
            File f = new File(user + ".txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String lineA = bfr.readLine();
            ArrayList<String> editedProduct = new ArrayList<>();
            while (lineA != null) {
                if (lineA.contains(storeName) && lineA.contains(productName)) {
                        data.add(seller2.toString());
                } else {
                    data.add(lineA);
                }
                lineA = bfr.readLine();
            }
            bfr.close();
            PrintWriter pw = new PrintWriter(new FileWriter(f,false));
            for (int i = 0; i < data.size(); i++) {
                pw.println(data.get(i));
            }
            pw.flush();
            pw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteProduct(String productName, String storeName, String productDescription, int productQuantity, double productPrice, String user) {
        Product product3 = new Product(productName,storeName,productDescription,productQuantity,productPrice);
        String data = "";
        ArrayList<String> fixedData = new ArrayList<>();
        try {
            File f = new File(user + ".txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.contains(productName))) {
                    fixedData.add(line);
                }
                line = bfr.readLine();
            }
            for (int i =0; i < fixedData.size(); i++) {
                pw.println(fixedData.get(i));
            }
            return true;
            /*File f = new File(user + ".txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f,false));
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.contains(productName))) {
                    fixedData += line;
                    line = bfr.readLine();
                    if (line != null) {
                        fixedData += "\n";
                    }
                } else {
                    line = bfr.readLine();
                }
            }
            bfw.write(fixedData);
            bfw.flush();
            bfw.close();
            return true;
        */
        } catch (Exception e) {
            return false;
        }
    }
    /*public ArrayList<String> sellerDashboard(String sellerName) {
        ArrayList<String> customerListINFO = new ArrayList<>();
        ArrayList<String> sellerDashboard = new ArrayList<>();
        try {
            File f1 = new File("accounts.txt");
            ArrayList<String> customerAccounts = new ArrayList<String>();
            BufferedReader bfr2 = new BufferedReader(new FileReader(f1));
            String account = bfr2.readLine();
            while (account != null) {
                String[] eachLine = account.split(";");
                if (Integer.parseInt(eachLine[3]) == 1) {
                    customerAccounts.add(account.split(";")[0]);
                }
                account = bfr2.readLine();
            }

            for (int i = 0; i < customerAccounts.size(); i++) {
                File f = new File(customerAccounts.get(i) + "history.txt");
                BufferedReader bfr = new BufferedReader(new FileReader(f));
                String line = bfr.readLine();
                while (line != null) {
                    if (line.contains(sellerName)) {
                        customerListINFO.add(line);
                    }
                    line = bfr.readLine();
                }
            }
            File userFile = new File(sellerName + ".txt");
            for (int i = 0; i < customerListINFO.size(); i++) {
                sellerDashboard.add(customerListINFO.get(i));
            }
            for (int i = 0; i < sellerDashboard.size(); i++) {
                String[] arr1 = sellerDashboard.get(i).split(",");

            }


        } catch (Exception e) {
            return null;
        }
    }*/
    public void sellerViewCustomerPurchase(String sellerName) {
        ArrayList<String> customerListINFO = new ArrayList<>();
        try {
            File f1 = new File("accounts.txt");
            ArrayList<String> customerAccounts = new ArrayList<String>();
            BufferedReader bfr2 = new BufferedReader(new FileReader(f1));
            String account = bfr2.readLine();
            while (account != null) {
                String[] eachLine = account.split(";");
                if (Integer.parseInt(eachLine[3]) == 1) {
                    customerAccounts.add(account.split(";")[0]);
                }
                account = bfr2.readLine();
            }

            for (int i = 0; i < customerAccounts.size(); i++) {
                File f = new File(customerAccounts.get(i) + "cart.txt");
                BufferedReader bfr = new BufferedReader(new FileReader(f));
                String line = bfr.readLine();
                while (line != null) {
                    if (line.contains(sellerName)) {
                        customerListINFO.add(line);
                    }
                    line = bfr.readLine();
                }
            }
            File newFile = new File(sellerName + "shoppingcart.txt");
            newFile.createNewFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
            for (int i = 0; i < customerListINFO.size(); i++) {
                printWriter.println(customerListINFO.get(i));
            }
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            return;
        }
    }
    public String toString() {
        return (user + "," + super.toString());
    }

    


}
