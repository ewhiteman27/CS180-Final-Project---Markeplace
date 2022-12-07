import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;
import java.util.ArrayList;

/**
 * A Client class
 * It handles everything that deals with the GUI and sends information to the server
 *
 * @version 1.0
 * @authors saujinpark park1485 and
 */
public class Client {

    public static void main(String[] args) throws IOException {
        User user = new User();
        NewProduct product = new NewProduct();
        int hostNumber = 4242;
        String hostName = "localhost";
        String[] createLogIn = new String[2];
        createLogIn[0] = "Create new account";
        createLogIn[1] = "Log into existing account";
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 4242);
        BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter send = new PrintWriter(socket.getOutputStream());

        boolean startMenu = true;
        try {
            while (startMenu) {
                JOptionPane.showMessageDialog(null, "Welcome to the Purdue Marketplace!", "Market", JOptionPane.OK_OPTION);
                String choice = (String) JOptionPane.showInputDialog(null,
                        "Please select one of the available options", "Market",
                        JOptionPane.QUESTION_MESSAGE, null, createLogIn, createLogIn[0]);
                send.println(choice);
                send.flush();

                if (choice.equals(createLogIn[0])) {
                    String[] buySellOption = new String[2];
                    buySellOption[0] = "Buyer";
                    buySellOption[1] = "Seller";
                    boolean hasTheAccountBeenCreated = false;
                    String username = "";
                    String password = "";
                    String email = "";
                    String buyerSeller = "";
                    do {

                        username = (String) JOptionPane.showInputDialog(null, "Please enter a Username", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(username);
                        send.flush();

                        password = (String) JOptionPane.showInputDialog(null, "Please enter a Password", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(password);
                        send.flush();

                        email = (String) JOptionPane.showInputDialog(null, "Please enter a Email", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(email);
                        send.flush();

                        buyerSeller = (String) JOptionPane.showInputDialog(null, "Will you be using this application as a seller or a buyer?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buySellOption, buySellOption[0]);
                        send.println(buyerSeller);
                        send.flush();
                        //Sender 01


                        String xORy = receive.readLine(); // writer of 45/48

                        if (xORy.equalsIgnoreCase("x")) { //Error message that shows if the account creation failed
                            JOptionPane.showMessageDialog(null, "Error! One of your credentials was invalid. Please try again.", "Market", JOptionPane.ERROR_MESSAGE);
                        }


                        if (xORy.equalsIgnoreCase("y")) { //message that shows when the account is created
                            hasTheAccountBeenCreated = true;
                            JOptionPane.showMessageDialog(null, "The account was created successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } while (hasTheAccountBeenCreated == false);

                } else {
                    startMenu = false; // stops the start menu from showing up again when the user decides to log in
                    int yesNoLogIn = JOptionPane.showConfirmDialog(null, "Are you sure that you want to log in?", "Market", JOptionPane.YES_NO_OPTION);
                    if (yesNoLogIn == 1) {

                        send.println(Integer.toString(yesNoLogIn)); //tells the server that the client wants to go back to the start
                        send.flush();
                        //sender 02

                        startMenu = true; //This makes the start menu show up again

                    } else {
                        send.println(Integer.toString(yesNoLogIn)); //tells the server that the client wants to continue logging in
                        send.flush();
                        boolean isUserLoggedIn = false;
                        do {
                            String username = (String) JOptionPane.showInputDialog(null, "Please enter your username", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(username);
                            send.flush();

                            String password = (String) JOptionPane.showInputDialog(null, "Please enter your password", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(password);
                            send.flush();
                            //Sender 03

                            String yOrn = receive.readLine();

                            if (yOrn.equalsIgnoreCase("n")) {
                                JOptionPane.showMessageDialog(null, "Log In failed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                            if (yOrn.equalsIgnoreCase("y")) {
                                JOptionPane.showMessageDialog(null, "Log In successful!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                isUserLoggedIn = true;
                            }

                        } while (isUserLoggedIn == false);

                    }
                }
            }
            // END OF LOGIN










            int theUserAccountType = Integer.parseInt(receive.readLine());
            send.println(theUserAccountType);
            send.flush();
            //sender04
            String buyerFirstResponse = "";
            String sellerFirstResponse = "";
            String[] buyerOptions = new String[7];
            buyerOptions[0] = "View All Available Products";
            buyerOptions[1] = "Sort The Marketplace";
            buyerOptions[2] = "Edit Account";
            buyerOptions[3] = "Delete Account";
            buyerOptions[4] = "View Cart";
            buyerOptions[5] = "View Purchase History";
            buyerOptions[6] = "Log Out";
            if (theUserAccountType == 1) {
                boolean whileBuying = false;
                do {
                    JOptionPane.showMessageDialog(null, "Welcome Buyer!", "Market", JOptionPane.INFORMATION_MESSAGE);
                    buyerFirstResponse = (String) JOptionPane.showInputDialog(null, "What would you like to do?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buyerOptions, buyerOptions[0]);

                    send.println(buyerFirstResponse);
                    send.flush();
                    //sender 05

                    if (buyerFirstResponse.equals(buyerOptions[0])) { //view all products
                        int sizeOfProductsArray = product.getProducts().size();
                        String[] fillingList = new String[sizeOfProductsArray];
                        for (int i = 0; i < sizeOfProductsArray; i++) {
                            fillingList[i] = product.getProducts().get(i);
                        }

                        JOptionPane.showInputDialog(null, "Here are all of the available products!", "Market", JOptionPane.INFORMATION_MESSAGE, null, fillingList, fillingList[0]);
                    } else if (buyerFirstResponse.equals((buyerOptions[1]))) { //sort


                    } else if (buyerFirstResponse.equals((buyerOptions[2]))) { // edit account
                        String[] editProfile = new String[3];
                        editProfile[0] = "Change Username";
                        editProfile[1] = "Change Password";
                        editProfile[2] = "Change Email";
                        String editChoice = (String) JOptionPane.showInputDialog(null, "What would you like to change?", "Market", JOptionPane.INFORMATION_MESSAGE, null, editProfile, editProfile[0]);

                        send.println(editChoice);
                        send.flush();
                        //Sender 06

                        if (editChoice.equals(editProfile[0])) { //Username change
                            String emailStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(emailStored);
                            send.flush();
                            //sender 07

                            String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(passwordStored);
                            send.flush();
                            //sender 08

                            String newUsernameStored = (String) JOptionPane.showInputDialog(null, "Enter your desired Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newUsernameStored);
                            send.flush();
                            //sender 09

                            String confirmChange = receive.readLine();
                            //receiver 13
                            if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Username was not changed! Either email/password was incorrect " +
                                        "or the username you selected was already taken.", "Market", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Username change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else if (editChoice.equals(editProfile[1])) { //Password change
                            String emailStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(emailStored);
                            send.flush();
                            //sender 10

                            String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(usernameStored);
                            send.flush();
                            //sender 11

                            String newPasswordStored = (String) JOptionPane.showInputDialog(null, "Enter your new Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newPasswordStored);
                            send.flush();
                            //sender 12

                            String confirmChange = receive.readLine(); //checks if credentials and new password are valid
                            //receiver 14

                            if (confirmChange.equalsIgnoreCase("true")) {
                                JOptionPane.showMessageDialog(null, "Password change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Password was not changed! Either password length was not longer than 8 characters " +
                                        "or the username/email was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else { //change Email
                            String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(usernameStored);
                            send.flush();
                            //sender 15

                            String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(passwordStored);
                            send.flush();
                            //sender 16

                            String newEmailStored = (String) JOptionPane.showInputDialog(null, "Enter your new Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newEmailStored);
                            send.flush();
                            //sender 17

                            String confirmChange = receive.readLine(); //checks if credentials and new Email are valid
                            //receiver 18

                            if (confirmChange.equalsIgnoreCase("true")) {
                                JOptionPane.showMessageDialog(null, "Email change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Email was not changed! Either Email is already in use " +
                                        "or the username/password was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }

                    } else if (buyerFirstResponse.equals((buyerOptions[3]))) { //delete account
                        String usernameStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter username:", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(usernameStored);
                        send.flush();
                        //sender 19

                        String passwordStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter password:", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(passwordStored);
                        send.flush();
                        //sender 20

                        String confirmDelete = receive.readLine();
                        //receiver 21
                        if (confirmDelete.equalsIgnoreCase("true")) {
                            JOptionPane.showMessageDialog(null, "Account was deleted!" +
                                    "", "Market", JOptionPane.INFORMATION_MESSAGE);
                            whileBuying = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Account was not deleted! Either" +
                                    "username/password was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (buyerFirstResponse.equals((buyerOptions[4]))) {

                    } else if (buyerFirstResponse.equals((buyerOptions[5]))) {

                    } else if (buyerFirstResponse.equals((buyerOptions[6]))) {
                        JOptionPane.showMessageDialog(null, "Thank you for using the Market!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        whileBuying = true;
                    }
                } while (whileBuying == false);


            }  //END OF BUYER








            //START OF SELLER
            if (theUserAccountType == 2) {
                String[] sellerOptions = new String[8];
                sellerOptions[0] = "Edit Account";
                sellerOptions[1] = "Delete Account";
                sellerOptions[2] = "Create New Product";
                sellerOptions[3] = "Edit Product";
                sellerOptions[4] = "Delete Product";
                sellerOptions[5] = "Import Product File";
                sellerOptions[6] = "Export File";
                sellerOptions[7] = "Log Out";
                boolean whileSelling = false;
                do {
                    JOptionPane.showMessageDialog(null, "Welcome Seller!", "Market", JOptionPane.INFORMATION_MESSAGE);
                    sellerFirstResponse = (String) JOptionPane.showInputDialog(null, "What would you like to do?", "Market", JOptionPane.INFORMATION_MESSAGE, null, sellerOptions, sellerOptions[0]);

                    send.println(sellerFirstResponse);
                    send.flush();
                    //sender 22

                    if (sellerFirstResponse.equals(sellerOptions[0])) {
                        String[] editProfile = new String[3];
                        editProfile[0] = "Change Username";
                        editProfile[1] = "Change Password";
                        editProfile[2] = "Change Email";
                        String editChoice = (String) JOptionPane.showInputDialog(null, "What would you like to change?", "Market", JOptionPane.INFORMATION_MESSAGE, null, editProfile, editProfile[0]);

                        send.println(editChoice);
                        send.flush();
                        //Sender 06

                        if (editChoice.equals(editProfile[0])) { //Username change
                            String emailStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(emailStored);
                            send.flush();
                            //sender 07

                            String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(passwordStored);
                            send.flush();
                            //sender 08

                            String newUsernameStored = (String) JOptionPane.showInputDialog(null, "Enter your desired Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newUsernameStored);
                            send.flush();
                            //sender 09

                            String confirmChange = receive.readLine();
                            //receiver 13
                            if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Username was not changed! Either email/password was incorrect " +
                                        "or the username you selected was already taken.", "Market", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Username change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else if (editChoice.equals(editProfile[1])) { //Password change
                            String emailStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(emailStored);
                            send.flush();
                            //sender 10

                            String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(usernameStored);
                            send.flush();
                            //sender 11

                            String newPasswordStored = (String) JOptionPane.showInputDialog(null, "Enter your new Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newPasswordStored);
                            send.flush();
                            //sender 12

                            String confirmChange = receive.readLine(); //checks if credentials and new password are valid
                            //receiver 14

                            if (confirmChange.equalsIgnoreCase("true")) {
                                JOptionPane.showMessageDialog(null, "Password change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Password was not changed! Either password length was not longer than 8 characters " +
                                        "or the username/email was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else { //change Email
                            String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(usernameStored);
                            send.flush();
                            //sender 15

                            String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(passwordStored);
                            send.flush();
                            //sender 16

                            String newEmailStored = (String) JOptionPane.showInputDialog(null, "Enter your new Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                            send.println(newEmailStored);
                            send.flush();
                            //sender 17

                            String confirmChange = receive.readLine(); //checks if credentials and new Email are valid
                            //receiver 18

                            if (confirmChange.equalsIgnoreCase("true")) {
                                JOptionPane.showMessageDialog(null, "Email change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirmChange.equalsIgnoreCase("false")) {
                                JOptionPane.showMessageDialog(null, "Email was not changed! Either Email is already in use " +
                                        "or the username/password was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }

                    } else if (sellerFirstResponse.equals(sellerOptions[1])) {
                        String usernameStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter username:", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(usernameStored);
                        send.flush();
                        //sender 23

                        String passwordStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter password:", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(passwordStored);
                        send.flush();
                        //sender 24

                        String confirmDelete = receive.readLine();
                        //receiver 25
                        if (confirmDelete.equalsIgnoreCase("true")) {
                            JOptionPane.showMessageDialog(null, "Account was deleted!" +
                                    "", "Market", JOptionPane.INFORMATION_MESSAGE);
                            whileSelling = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Account was not deleted! Either" +
                                    "username/password was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (sellerFirstResponse.equals(sellerOptions[2])) { //create product
                        String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String productName = JOptionPane.showInputDialog(null, "Enter the product name:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String description = JOptionPane.showInputDialog(null, "Enter the product description:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String quantity = JOptionPane.showInputDialog(null, "Enter the quantity:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String price = JOptionPane.showInputDialog(null, "Enter the price:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        
                        send.println(storeName);  //sender 30
                        send.flush();
                        send.println(productName);
                        send.flush();
                        send.println(description);
                        send.flush();
                        send.println(quantity);
                        send.flush();
                        send.println(price);
                        send.flush();
                        String confirmListing = receive.readLine();
                        if (confirmListing.equalsIgnoreCase("y")) {
                            JOptionPane.showMessageDialog(null, "Product was created!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        } else if (confirmListing.equalsIgnoreCase("n")) {
                            JOptionPane.showMessageDialog(null, "Product creation failed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else if (sellerFirstResponse.equals(sellerOptions[3])) { //edit product
                        String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store that the product is listed:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String productName = JOptionPane.showInputDialog(null, "Enter the product name:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String newStoreName = JOptionPane.showInputDialog(null, "Enter the new Store Name:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String newProductName = JOptionPane.showInputDialog(null, "Enter the new Product Name:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String newDescription = JOptionPane.showInputDialog(null, "Enter the new Description:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String newQuantity = JOptionPane.showInputDialog(null, "Enter the new Quantity:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String newPrice = JOptionPane.showInputDialog(null, "Enter the new price:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(storeName);  //sender 31
                        send.flush();
                        send.println(productName);
                        send.flush();
                        send.println(newStoreName);
                        send.flush();
                        send.println(newProductName);
                        send.flush();
                        send.println(newDescription);
                        send.flush();
                        send.println(newQuantity);
                        send.flush();
                        send.println(newPrice);
                        send.flush();

                        String confirmEdit = receive.readLine(); //receiver 32
                        if (confirmEdit.equalsIgnoreCase("y")) {
                            JOptionPane.showMessageDialog(null, "Product was edited!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        } else if (confirmEdit.equalsIgnoreCase("n")) {
                            JOptionPane.showMessageDialog(null, "Product edit failed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else if (sellerFirstResponse.equals(sellerOptions[4])) { //delete product
                        String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store that the product is listed:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        String productName = JOptionPane.showInputDialog(null, "Enter the name of the product:" , "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(storeName);  //sender 32
                        send.flush();
                        send.println(productName);
                        send.flush();
                        String confirmDelete = receive.readLine(); //receiver 33
                        if (confirmDelete.equalsIgnoreCase("y")) {
                            JOptionPane.showMessageDialog(null, "Product was successfully removed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        } else if (confirmDelete.equalsIgnoreCase("n")) {
                            JOptionPane.showMessageDialog(null, "Product removal failed!", "Market", JOptionPane.INFORMATION_MESSAGE);

                        }

                    } else if (sellerFirstResponse.equals(sellerOptions[5])) {

                    } else if (sellerFirstResponse.equals(sellerOptions[6])) {

                    } else if (sellerFirstResponse.equals(sellerOptions[7])) { //log out
                        JOptionPane.showMessageDialog(null, "Thank you for using the Market!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        whileSelling = true;
                    }

                } while (whileSelling == false);
            }


        } catch (Exception e) {
            throw e;
        }


    }
}
