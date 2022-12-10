import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * A Server class
 * It handles everything that deals with processing the user's inputs and runs the methods.
 *
 * @authors saujinpark and
 * @version 1.0
 */
public class Server {

    public static void main(String[] args) throws IOException {
        String[] createLogIn = new String[2];
        createLogIn[0] = "Create new account";
        createLogIn[1] = "Log into existing account";
        User edits = new User();
        ServerSocket serverSocket = new ServerSocket(4242);
        Socket socket = serverSocket.accept();
        BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter send = new PrintWriter(socket.getOutputStream());
        NewProduct product = new NewProduct();
        //end of necessary things

        boolean logInMenu = false;
        do {
            String choice = receive.readLine(); //reads if user creates an account or logs in
            if (choice != null) {
                User user = new User();
                boolean hasTheAccountBeenCreated;
                if (choice.equals(createLogIn[0])) {
                    //Verifying that creating an account is successful
                    do {

                        String username = receive.readLine();
                        if (username != null) {

                            String password = receive.readLine();
                            if (password != null) {
                                String email = receive.readLine();
                                if (email != null) {
                                    String buyerSeller = receive.readLine();
                                    if (buyerSeller != null) {
                                        if (buyerSeller.equalsIgnoreCase("buyer")) {
                                            try {
                                                user = user.createAccount(username, email, password, 1);
                                                String yVariable = "y";
                                                send.println(yVariable); // Reader on 54
                                                send.flush();
                                                hasTheAccountBeenCreated = true;
                                            } catch (Exception e) {
                                                String xVariable = "x";
                                                send.println(xVariable); // Reader on 54
                                                send.flush();

                                            }
                                        } else if (buyerSeller.equalsIgnoreCase("seller")) {
                                            try {
                                                user = user.createAccount(username, email, password, 2);
                                                String yVariable = "y";
                                                send.println(yVariable); // Reader on 54
                                                send.flush();
                                            } catch (Exception e) {
                                                String xVariable = "x";
                                                send.println(xVariable);
                                                send.flush();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        hasTheAccountBeenCreated = true;


                        //Receiver 01


                    } while (hasTheAccountBeenCreated == false);

                    //end of account creation


                } else if (choice.equals(createLogIn[1])) { //LOGGING IN
                    boolean hasLoggedIn = false;
                    do {

                        String username = receive.readLine();
                        if (username != null) {
                            String password = receive.readLine();
                            if (password != null) {
                                try {
                                    user = user.logIn(username, password);
                                    edits = user;
                                    String yOrn = "y";
                                    send.println(yOrn);
                                    send.flush();
                                    send.println(user.getAccountType());
                                    send.flush();
                                    logInMenu = true;

                                } catch (Exception e) { //exception is caught when user fails to log in

                                    String yOrn = "n";
                                    send.println(yOrn);
                                    send.flush();
                                }

                            }
                        }

                        hasLoggedIn = true;

                    } while (hasLoggedIn == false);

                }
            }
        } while (logInMenu == false);
        //end of login portion




        String userAccountType = receive.readLine(); //checks to see if the user is a buyer or seller
        //receive 04

        if (userAccountType.equalsIgnoreCase("1")) {  //If user is a buyer
            NewBuyer buy = new NewBuyer(edits.getUsername()); //gives access to buyer methods
            String[] buyerOptions = new String[9];
            buyerOptions[0] = "View All Available Products"; //done
            buyerOptions[1] = "Sort The Marketplace"; //framed
            buyerOptions[2] = "Edit Account"; //done
            buyerOptions[3] = "Delete Account"; //done
            buyerOptions[4] = "View, Purchase, Add/Remove Items From Cart"; //done
            buyerOptions[5] = "Export Purchase History"; //done
            buyerOptions[6] = "Log Out"; //done
            buyerOptions[7] = "View a Product's Details"; //done
            buyerOptions[8] = "Search"; //done
            boolean whileBuying = false;

            do {
                String buyerFirstResponse = receive.readLine();
                if (buyerFirstResponse.equals(buyerOptions[0])) { //view all products
                    int sizeOfProductsArray = buy.formatProducts(product.getProducts()).size();
                    String[] fillingList = new String[sizeOfProductsArray];
                    for (int i = 0; i < sizeOfProductsArray; i++) {
                        fillingList[i] = buy.formatProducts(product.getProducts()).get(i);
                    }
                    try {
                        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                        objectOutput.writeObject(fillingList);
                    } catch (Exception e) {
                        String n = "n";
                    }



                } else if (buyerFirstResponse.equals((buyerOptions[1]))) { //sort
                    String[] sortType = new String[2];
                    sortType[0] = "Price";
                    sortType[1] = "Quantity";
                    String type = receive.readLine();
                    ArrayList<String> sort = new ArrayList<>();
                    if (type.equalsIgnoreCase(sortType[0])) { //price
                        sort = buy.sortPrice();
                    } else if (type.equalsIgnoreCase(sortType[1])) { //quantity
                        sort = buy.sortQuantity();
                    }
                    ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                    objectOutput.writeObject(sort);




                } else if (buyerFirstResponse.equals((buyerOptions[2]))) {
                    String[] editProfile = new String[3];
                    editProfile[0] = "Change Username";
                    editProfile[1] = "Change Password";
                    editProfile[2] = "Change Email";

                    String editChoice = receive.readLine(); //receiver 06

                    if (editChoice.equals(editProfile[0])) { //username change

                        String emailStored = receive.readLine();
                        //receiver 07

                        String passwordStored = receive.readLine();
                        //receiver 08

                        String newUsernameStored = receive.readLine();
                        //receiver 09

                        boolean changeUsername = edits.changeUsername(newUsernameStored, passwordStored, emailStored);
                        String confirmChange;

                        if (changeUsername) {
                            confirmChange = "true";
                        } else {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();
                        //sender 13

                    } else if (editChoice.equals(editProfile[1])) {  //password change
                        String emailStored = receive.readLine();
                        //receiver 10

                        String usernameStored = receive.readLine();
                        //receiver 11

                        String newPasswordStored = receive.readLine();
                        //receiver 12
                        try {
                            boolean changePassword = edits.changePassword(newPasswordStored, usernameStored, emailStored);
                            String confirmChange;

                            if (changePassword) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                            send.println(confirmChange);
                            send.flush();
                            //sender 14
                        } catch (Exception e) {
                            String confirmChange = "false";
                            send.println(confirmChange);
                            send.flush();
                            //sender 14
                        }

                    } else if (editChoice.equals(editProfile[2])) {  //emailchange
                        String usernameStored = receive.readLine();
                        //receiver 15

                        String passwordStored = receive.readLine();
                        //receiver 16

                        String newEmailStored = receive.readLine();
                        //receiver 17
                        String confirmChange;
                        try {
                            boolean changeEmail = edits.changeEmail(newEmailStored, usernameStored, passwordStored);

                            if (changeEmail) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                        } catch (Exception e) {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();
                        //sender 18

                    }

                } else if (buyerFirstResponse.equals((buyerOptions[3]))) { //delete account
                    String usernameStored = receive.readLine();
                    //receiver 19

                    String passwordStored = receive.readLine();
                    //receiver 20

                    String confirmDelete;
                    try {
                        boolean delete = edits.deleteAccount(usernameStored, passwordStored);
                        if (delete) {
                            confirmDelete = "true";
                            whileBuying = true;
                        } else {
                            confirmDelete = "false";
                        }
                    } catch (Exception e) {
                        confirmDelete = "false";
                    }

                    send.println(confirmDelete);
                    send.flush();
                    //sender 21

                } else if (buyerFirstResponse.equals((buyerOptions[4]))) { //view cart plus
                    String[] cartOptions = new String[4];
                    cartOptions[0] = "Add Item to Cart";
                    cartOptions[1] = "Remove Item From Cart";
                    cartOptions[2] = "Go Back to Main Menu";
                    cartOptions[3] = "View Items in Cart";


                    String cartChoice = receive.readLine();
                    if (cartChoice.equalsIgnoreCase("purchase")) {
                        boolean purchased = true;
                        int loop = buy.numInCart();
                        for (int i = 0; i < loop; i++) {
                            purchased = buy.buy();
                            if (!purchased) {
                                send.println("n");
                                send.flush();
                                break;
                            }
                        }
                        if (purchased) {
                            send.println("y");
                            send.flush();
                        }

                    }

                    if (cartChoice.equalsIgnoreCase(cartOptions[0])) { //add to cart
                        String storeName = receive.readLine();
                        String productName = receive.readLine();
                        String quantity = receive.readLine();
                        try {
                            int formatQuantity = Integer.parseInt(quantity);
                            boolean bought = buy.addToCart(storeName, productName, formatQuantity);
                            if (bought) {
                                String confirmAdd = "y";
                                send.println(confirmAdd);
                                send.flush();
                            } else {
                                String confirmAdd = "n";
                                send.println(confirmAdd);
                                send.flush();
                            }


                        } catch (Exception e) {
                            String confirmAdd = "n";
                            send.println(confirmAdd);
                            send.flush(); //sender confirm add
                        }

                    } else if (cartChoice.equalsIgnoreCase(cartOptions[1])) { //remove from cart
                        ArrayList<String> cart = buy.getBuyerCart();
                        try {
                            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                            objectOutput.writeObject(cart);
                        } catch (Exception e) {
                            String n = "n";
                        }
                        String goStop = receive.readLine();
                        if (goStop.equalsIgnoreCase("go")) {
                            String storeName = receive.readLine();
                            String productName = receive.readLine();
                            String confirmRemove;


                            try {
                                boolean remove = buy.removeFromCart(storeName, productName);
                                if (remove) {
                                    confirmRemove = "y";
                                } else {
                                    confirmRemove = "n";
                                }
                                send.println(confirmRemove);
                                send.flush();
                            } catch (Exception e) {
                                confirmRemove = "n";
                                send.println(confirmRemove);
                                send.flush();
                            }
                        }


                    } else if (cartChoice.equalsIgnoreCase(cartOptions[2])) { //main menu
                        String haha = "Smile and Wave";
                    } else if (cartChoice.equalsIgnoreCase(cartOptions[3])) { //view
                        ArrayList<String> cart = buy.getBuyerCart();
                        try {
                            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                            objectOutput.writeObject(cart);
                        } catch (Exception e) {
                            String n = "n";
                        }
                    }

                } else if (buyerFirstResponse.equals((buyerOptions[5]))) { //export purchase history
                    String filePath = receive.readLine(); //receiver filepath
                    try {
                        boolean export = buy.exportFile(filePath);
                        String confirm;
                        if (export) {
                            confirm = "y";
                        } else {
                            confirm = "n";
                        }
                        send.println(confirm);
                        send.flush();
                    } catch (Exception e) {
                        String confirm = "n";
                        send.println(confirm);
                        send.flush();
                    }
                } else if (buyerFirstResponse.equals((buyerOptions[6]))) {
                    whileBuying = true;
                } else if (buyerFirstResponse.equalsIgnoreCase(buyerOptions[7])) { //view product details  //IT is giving me each individual index instead of the combined string
                    String storeName = receive.readLine();
                    String productName = receive.readLine();
                    String withDescription = buy.getFormattedProduct(productName, storeName);

                    send.println(withDescription);
                    send.flush();
                } else if (buyerFirstResponse.equalsIgnoreCase(buyerOptions[8])) {
                    String searchTerm = receive.readLine();
                    ArrayList<String> results = buy.searchProduct(searchTerm);
                    ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                    objectOutput.writeObject(results);

                }

            } while (whileBuying == false);






















        

        

        } else if (userAccountType.equalsIgnoreCase("2")) { //If user is a seller
            NewSeller sell = new NewSeller(edits.getUsername()); // gives access to seller methods
            String[] sellerOptions = new String[10];
            sellerOptions[0] = "Edit Account"; //done
            sellerOptions[1] = "Delete Account"; //done
            sellerOptions[2] = "Create New Product"; //done
            sellerOptions[3] = "Edit Product"; //done
            sellerOptions[4] = "Delete Product"; //done
            sellerOptions[5] = "Import Product File"; //done?
            sellerOptions[6] = "Export File"; //done?
            sellerOptions[7] = "Log Out"; //done
            sellerOptions[8] = "View Store Statistics";
            sellerOptions[9] = "View Cart Information";
            boolean whileSelling = false;

            do {
                String sellerResponse = receive.readLine();

                if (sellerResponse.equals(sellerOptions[0])) {
                    String[] editProfile = new String[3];
                    editProfile[0] = "Change Username";
                    editProfile[1] = "Change Password";
                    editProfile[2] = "Change Email";

                    String editChoice = receive.readLine(); //receiver 06

                    if (editChoice.equals(editProfile[0])) { //username change

                        String emailStored = receive.readLine();


                        String passwordStored = receive.readLine();


                        String newUsernameStored = receive.readLine();


                        boolean changeUsername = edits.changeUsername(newUsernameStored, passwordStored, emailStored);
                        String confirmChange;

                        if (changeUsername) {
                            confirmChange = "true";
                        } else {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();

                    } else if (editChoice.equals(editProfile[1])) {  //password change
                        String emailStored = receive.readLine();


                        String usernameStored = receive.readLine();


                        String newPasswordStored = receive.readLine();

                        try {
                            boolean changePassword = edits.changePassword(newPasswordStored, usernameStored, emailStored);
                            String confirmChange;

                            if (changePassword) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                            send.println(confirmChange);
                            send.flush();

                        } catch (Exception e) {
                            String confirmChange = "false";
                            send.println(confirmChange);
                            send.flush();

                        }

                    } else if (editChoice.equals(editProfile[2])) {  //emailchange
                        String usernameStored = receive.readLine();


                        String passwordStored = receive.readLine();


                        String newEmailStored = receive.readLine();

                        String confirmChange;
                        try {
                            boolean changeEmail = edits.changeEmail(newEmailStored, usernameStored, passwordStored);

                            if (changeEmail) {
                                confirmChange = "true";
                            } else {
                                confirmChange = "false";
                            }

                        } catch (Exception e) {
                            confirmChange = "false";
                        }

                        send.println(confirmChange);
                        send.flush();


                    }

                } else if (sellerResponse.equals(sellerOptions[1])) {
                    String usernameStored = receive.readLine();

                    String passwordStored = receive.readLine();

                    String confirmDelete;
                    try {
                        boolean delete = edits.deleteAccount(usernameStored, passwordStored);
                        if (delete) {
                            confirmDelete = "true";
                            whileSelling = true;
                        } else {
                            confirmDelete = "false";
                        }
                    } catch (Exception e) {
                        confirmDelete = "false";
                    }

                    send.println(confirmDelete);
                    send.flush();

                } else if (sellerResponse.equals(sellerOptions[2])) { //create product
                    String storeName = receive.readLine(); //receiver 30
                    String productName = receive.readLine();
                    String description = receive.readLine();
                    String quantity = receive.readLine();
                    String price = receive.readLine();
                    try {
                        int realQuantity = Integer.parseInt(quantity);
                        double realPrice = Double.parseDouble(price);

                        boolean listed = sell.createProduct(storeName, productName, description, realQuantity, realPrice);
                        if (storeName.equals("") || productName.equals("") || quantity.equals("") || price.equals("")) {
                            listed = false;
                        }
                        if (listed) {
                            send.println("y");
                            send.flush();
                        } else {
                            send.println("n");
                            send.flush();
                        }
                    } catch (Exception e) {
                        send.println("n");
                        send.flush();
                    }


                } else if (sellerResponse.equals(sellerOptions[3])) { //edit product
                    String storeName = receive.readLine(); //receiver 31
                    String productName = receive.readLine();
                    String newStore = receive.readLine();
                    String newName = receive.readLine();
                    String newDescription = receive.readLine();
                    String quantity = receive.readLine();
                    String price = receive.readLine();
                    try {
                        int newQuantity = Integer.parseInt(quantity);
                        double newPrice = Double.parseDouble(price);

                        boolean confirmEdit = sell.editProduct(storeName, productName, newStore, newName, newDescription, newQuantity, newPrice);
                        if (storeName.equals("") || productName.equals("") || newStore.equals("") || newName.equals("") ||
                                newDescription.equals("") || quantity.equals("") || price.equals("")) {
                            confirmEdit = false;
                        }
                        if (confirmEdit) {
                            send.println("y");    //sender32
                            send.flush();
                        } else {
                            send.println("n");
                            send.flush();
                        }
                    } catch (Exception e) {
                        send.println("n");
                        send.flush();
                    }

                } else if (sellerResponse.equals(sellerOptions[4])) { //delete product
                    String storeName = receive.readLine();
                    String productName = receive.readLine();
                    // receiver 32

                    try {
                        boolean delete = sell.deleteProduct(storeName, productName);        //sender 33
                        if (delete) {
                            String confirmDelete = "y";
                            send.println(confirmDelete);
                            send.flush();
                        } else {
                            String confirmDelete = "n";
                            send.println(confirmDelete);
                            send.flush();
                        }

                    } catch (Exception e) {
                        String confirmDelete = "n";
                        send.println(confirmDelete);
                        send.flush();
                    }

                } else if (sellerResponse.equals(sellerOptions[5])) { //import product file
                    String pathName = receive.readLine();
                    try {
                        boolean check = sell.importFile(pathName);
                        if (check) {
                            String confirm = "y";
                            send.println(confirm);
                            send.flush();
                        } else if (!check) {
                            String confirm = "n";
                            send.println(confirm);
                            send.flush();
                        }
                    } catch (Exception e) {
                        String confirm = "n";
                    }


                } else if (sellerResponse.equals(sellerOptions[6])) { //export
                    String pathName = receive.readLine(); //receiver pathName
                    try {
                        boolean check = sell.exportFile(pathName);
                        if (check) {
                            String confirm = "y";
                            send.println(confirm);
                            send.flush();
                        } else if (!check) {
                            String confirm = "n";
                            send.println(confirm);
                            send.flush();
                        }
                    } catch (Exception e) {
                        String confirm = "n";
                        send.println(confirm);
                        send.flush();
                    }

                } else if (sellerResponse.equals(sellerOptions[7])) { //log out
                    whileSelling = true;
                } else if (sellerResponse.equalsIgnoreCase(sellerOptions[8])) { //store stats
                    String storeName = receive.readLine();
                    try {
                        ArrayList<String> log;
                        log = sell.sellerLog(storeName);
                        try {
                            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                            objectOutput.writeObject(log);
                        } catch (Exception e) {
                            String hitwo = "";
                        }

                    } catch (Exception e) {
                        String hi = "";
                    }
                } else if (sellerResponse.equalsIgnoreCase(sellerOptions[9])) { //cart information
                    ArrayList<String> cartStats = sell.getSellerCart();//insertmethod
                    ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());  //OOS 1
                    objectOutput.writeObject(cartStats);


                }
            } while (whileSelling == false);


        }

    }

}
