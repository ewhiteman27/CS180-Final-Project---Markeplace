import java.io.*;
import java.net.*;
import java.util.Arrays;
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
                if (choice == null) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the Market!", "Market", JOptionPane.OK_OPTION);


                } else {

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
                            if (username != null) {
                                password = (String) JOptionPane.showInputDialog(null, "Please enter a Password (Password must be greater than 8 characters)", "Market", JOptionPane.INFORMATION_MESSAGE);
                                send.println(password);
                                send.flush();
                                if (password != null) {
                                    email = (String) JOptionPane.showInputDialog(null, "Please enter a Email", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    send.println(email);
                                    send.flush();
                                    if (email != null) {
                                        buyerSeller = (String) JOptionPane.showInputDialog(null, "Will you be using this application as a seller or a buyer?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buySellOption, buySellOption[0]);
                                        if (buyerSeller != null) {
                                            send.println(buyerSeller);
                                            send.flush();
                                            String xORy = receive.readLine(); // writer of 45/48

                                            if (xORy.equalsIgnoreCase("x")) { //Error message that shows if the account creation failed
                                                JOptionPane.showMessageDialog(null, "Error! One of your credentials was invalid. Please try again.", "Market", JOptionPane.ERROR_MESSAGE);
                                            }


                                            if (xORy.equalsIgnoreCase("y")) { //message that shows when the account is created
                                                JOptionPane.showMessageDialog(null, "The account was created successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    }
                                }
                            }
                            hasTheAccountBeenCreated = true;

                            //Sender 01


                        } while (hasTheAccountBeenCreated == false);

                    } else {
                        boolean isUserLoggedIn = true;
                        do {
                            String username = (String) JOptionPane.showInputDialog(null, "Please enter your username", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(username);
                            send.flush();
                            if (username != null) {

                                String password = (String) JOptionPane.showInputDialog(null, "Please enter your password", "Market", JOptionPane.INFORMATION_MESSAGE);
                                send.println(password);
                                send.flush();
                                if (password != null) {
                                    String yOrn = receive.readLine();

                                    if (yOrn.equalsIgnoreCase("n")) {
                                        JOptionPane.showMessageDialog(null, "Log In failed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    }

                                    if (yOrn.equalsIgnoreCase("y")) {
                                        JOptionPane.showMessageDialog(null, "Log In successful!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                        startMenu = false;
                                    }

                                }
                            }
                            isUserLoggedIn = false;
                        } while (isUserLoggedIn);


                    }
                }
                // END OF LOGIN


                int theUserAccountType = Integer.parseInt(receive.readLine());
                send.println(theUserAccountType);
                send.flush();
                //sender04
                String buyerFirstResponse = "";
                String sellerFirstResponse = "";
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
                if (theUserAccountType == 1) {  //buyer
                    boolean whileBuying = false;
                    do {
                        JOptionPane.showMessageDialog(null, "Welcome Buyer!", "Market", JOptionPane.INFORMATION_MESSAGE);
                        do {
                            buyerFirstResponse = (String) JOptionPane.showInputDialog(null, "What would you like to do?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buyerOptions, buyerOptions[0]);
                            if (buyerFirstResponse == null) {
                                JOptionPane.showMessageDialog(null, "Please select one of the options on the dropdown menu", "Market", JOptionPane.INFORMATION_MESSAGE);

                            }
                        } while (buyerFirstResponse == null);
                        send.println(buyerFirstResponse);
                        send.flush();
                        //sender 05

                        if (buyerFirstResponse.equals(buyerOptions[0])) { //view all products
                            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                            Object object = oi.readObject();
                            String[] allProducts = (String[]) object;

                            JOptionPane.showInputDialog(null, "Here are all of the available products!", "Market", JOptionPane.INFORMATION_MESSAGE, null, allProducts, allProducts[0]);
                        } else if (buyerFirstResponse.equals((buyerOptions[1]))) { //sort
                            String[] sortType = new String[2];
                            sortType[0] = "Price";
                            sortType[1] = "Quantity";
                            String sortResponse = (String) JOptionPane.showInputDialog(null, "How would you like to sort?", "Market", JOptionPane.INFORMATION_MESSAGE, null, sortType, sortType[0]);
                            if (sortResponse == null) {
                                String response = "n";
                                send.println(response);
                                send.flush();
                            } else {
                                send.println(sortResponse);
                                send.flush();

                                ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                                Object object = oi.readObject();
                                ArrayList<String> temp = (ArrayList<String>) object;
                                String[] sorted = new String[temp.size()];

                                temp.toArray(sorted);
                                String sortedProductsResponse = (String) JOptionPane.showInputDialog(null, "Here are the sorted products:", "Market", JOptionPane.INFORMATION_MESSAGE, null, sorted, sorted[0]);
                            }


                        } else if (buyerFirstResponse.equals((buyerOptions[2]))) { // edit account
                            String[] editProfile = new String[3];
                            editProfile[0] = "Change Username";
                            editProfile[1] = "Change Password";
                            editProfile[2] = "Change Email";
                            String editChoice = (String) JOptionPane.showInputDialog(null, "What would you like to change?", "Market", JOptionPane.INFORMATION_MESSAGE, null, editProfile, editProfile[0]);

                            //Sender 06
                            if (editChoice == null) {
                                String no = "n";
                                send.println(no);
                                send.flush();

                            } else {
                                send.println(editChoice);
                                send.flush();

                                if (editChoice.equals(editProfile[0])) { //Username change
                                    String emailStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                    send.println(emailStored);
                                    send.flush();
                                    //sender 07
                                    if (emailStored != null) {
                                        String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your username we must verify your email and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                        send.println(passwordStored);
                                        send.flush();
                                        //sender 08
                                        if (passwordStored != null) {
                                            String newUsernameStored = (String) JOptionPane.showInputDialog(null, "Enter your desired Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                            send.println(newUsernameStored);
                                            send.flush();
                                            //sender 09
                                            if (newUsernameStored != null) {
                                                String confirmChange = receive.readLine();
                                                //receiver 13
                                                if (confirmChange.equalsIgnoreCase("false")) {
                                                    JOptionPane.showMessageDialog(null, "Username was not changed! Either email/password was incorrect " +
                                                            "or the username you selected was already taken.", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Username change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                }


                                            }

                                        }
                                    }

                                } else if (editChoice.equals(editProfile[1])) { //Password change
                                    String emailStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                    send.println(emailStored);
                                    send.flush();
                                    //sender 10
                                    if (emailStored != null) {
                                        String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your password we must verify your email and username!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                        send.println(usernameStored);
                                        send.flush();
                                        //sender 11
                                        if (usernameStored != null) {
                                            String newPasswordStored = (String) JOptionPane.showInputDialog(null, "Enter your new Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                            send.println(newPasswordStored);
                                            send.flush();
                                            //sender 12
                                            if (newPasswordStored != null) {
                                                String confirmChange = receive.readLine(); //checks if credentials and new password are valid
                                                //receiver 14

                                                if (confirmChange.equalsIgnoreCase("true")) {
                                                    JOptionPane.showMessageDialog(null, "Password change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                                                } else if (confirmChange.equalsIgnoreCase("false")) {
                                                    JOptionPane.showMessageDialog(null, "Password was not changed! Either password length was not longer than 8 characters " +
                                                            "or the username/email was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                            }
                                        }
                                    }


                                } else { //change Email
                                    String usernameStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Username:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                    send.println(usernameStored);
                                    send.flush();
                                    //sender 15
                                    if (usernameStored != null) {

                                        String passwordStored = (String) JOptionPane.showInputDialog(null, "To change your Email we must verify your username and password!\n Enter Password:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                        send.println(passwordStored);
                                        send.flush();
                                        //sender 16
                                        if (passwordStored != null) {
                                            String newEmailStored = (String) JOptionPane.showInputDialog(null, "Enter your new Email:", "Market", JOptionPane.INFORMATION_MESSAGE);

                                            send.println(newEmailStored);
                                            send.flush();
                                            //sender 17
                                            if (newEmailStored != null) {
                                                String confirmChange = receive.readLine(); //checks if credentials and new Email are valid
                                                //receiver 18

                                                if (confirmChange.equalsIgnoreCase("true")) {
                                                    JOptionPane.showMessageDialog(null, "Email change successfully!", "Market", JOptionPane.INFORMATION_MESSAGE);

                                                } else if (confirmChange.equalsIgnoreCase("false")) {
                                                    JOptionPane.showMessageDialog(null, "Email was not changed! Either Email is already in use " +
                                                            "or the username/password was incorrect!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                        } else if (buyerFirstResponse.equals((buyerOptions[3]))) { //delete account
                            String usernameStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter username:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(usernameStored);
                            send.flush();
                            //sender 19
                            if (usernameStored != null) {

                                String passwordStored = (String) JOptionPane.showInputDialog(null, "To delete your account we must verify your username and password!\n Enter password:", "Market", JOptionPane.INFORMATION_MESSAGE);
                                send.println(passwordStored);
                                send.flush();
                                //sender 20
                                if (passwordStored != null) {
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
                                }
                            }


                        } else if (buyerFirstResponse.equals((buyerOptions[4]))) {  //view cart also add and remove items are also here
                            String[] cartOptions = new String[5];
                            cartOptions[0] = "Add Item to Cart";
                            cartOptions[1] = "Remove Item From Cart";
                            cartOptions[2] = "Go Back to Main Menu";
                            cartOptions[3] = "View Items in Cart";
                            cartOptions[4] = "Purchase";
                            String cartResponse = ""; // what the buyer chooses to do in the cart
                            try {
                                //end of formatting the arraylist of products
                                boolean goCart = true;
                                do {
                                    cartResponse = (String) JOptionPane.showInputDialog(null, "Select an action", "Market", JOptionPane.INFORMATION_MESSAGE, null, cartOptions, cartOptions[0]);
                                    if (cartResponse == null) {
                                        goCart = false;
                                    }
                                    if (cartResponse.equalsIgnoreCase("purchase")) {
                                        goCart = false;
                                    }
                                    if (cartResponse.equalsIgnoreCase(cartOptions[0])) {
                                        goCart = false;
                                    }
                                    if (cartResponse.equalsIgnoreCase(cartOptions[1])) {
                                        goCart = false;
                                    }
                                    if (cartResponse.equalsIgnoreCase(cartOptions[2])) {
                                        goCart = false;
                                    }
                                    if (cartResponse.equalsIgnoreCase(cartOptions[3])) {
                                        goCart = false;
                                    }
                                } while (goCart);


                            } catch (Exception e) {
                                String smile = "wave";
                            }

                            send.println(cartResponse);
                            send.flush();
                            if (cartResponse == null) {

                            } else {
                                if (cartResponse.equalsIgnoreCase("purchase")) { //purchase thank you message/uhoh
                                    String confirmPurchase = receive.readLine();
                                    if (confirmPurchase.equalsIgnoreCase("y")) {
                                        JOptionPane.showMessageDialog(null, "Thank you for your purchase!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    } else if (confirmPurchase.equalsIgnoreCase("n")) {
                                        JOptionPane.showMessageDialog(null, "Cart is Empty!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }


                                if (cartResponse.equalsIgnoreCase(cartOptions[0])) { //add item
                                    String storeName = (String) JOptionPane.showInputDialog(null, "Enter the name of the store that carries the product:", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    if (storeName != null) {
                                        String productName = (String) JOptionPane.showInputDialog(null, "Enter the name of the product:", "Market", JOptionPane.INFORMATION_MESSAGE);
                                        if (productName != null) {
                                            String quantity = (String) JOptionPane.showInputDialog(null, "Enter the number of items you want to add:", "Market", JOptionPane.INFORMATION_MESSAGE);
                                            if (quantity != null) {
                                                send.println(storeName);   //sender add to cart
                                                send.flush();
                                                send.println(productName);
                                                send.flush();
                                                send.println(quantity);
                                                send.flush();

                                                String confirmAdd = receive.readLine();
                                                if (confirmAdd.equalsIgnoreCase("y")) {
                                                    JOptionPane.showMessageDialog(null, "Item added to cart!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                } else if (confirmAdd.equalsIgnoreCase("n")) {
                                                    JOptionPane.showMessageDialog(null, "Failed to add item to cart!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                                }

                                            }
                                        }
                                    }


                                } else if (cartResponse.equalsIgnoreCase(cartOptions[1])) { // remove item
                                    ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                                    Object object = oi.readObject();
                                    ArrayList<String> temp = (ArrayList<String>) object;
                                    ArrayList<String> allProducts = temp;
                                    String[] completeList = new String[allProducts.size()];
                                    allProducts.toArray(completeList);
                                    if (allProducts.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Your cart is empty", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        String removeThis = (String) JOptionPane.showInputDialog(null, "Select an item to remove it.", "Market", JOptionPane.INFORMATION_MESSAGE, null, completeList, completeList[0]);
                                        if (removeThis != null) {
                                            String go = "go";
                                            send.println(go);
                                            send.flush();

                                            String[] parts = removeThis.split(",");
                                            String storeName = parts[1];
                                            String productName = parts[2];
                                            send.println(storeName);   //sender remove from cart
                                            send.flush();
                                            send.println(productName);
                                            send.flush();

                                            String confirmRemove = receive.readLine();
                                            if (confirmRemove.equalsIgnoreCase("y")) {
                                                JOptionPane.showMessageDialog(null, "Item removed from cart!", "Market", JOptionPane.INFORMATION_MESSAGE);

                                            } else if (confirmRemove.equalsIgnoreCase("n")) {
                                                JOptionPane.showMessageDialog(null, "Item removal failed!", "Market", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        } else {
                                            String stop = "stop";
                                            send.println(stop);
                                            send.flush();
                                        }
                                    }


                                } else if (cartResponse.equalsIgnoreCase(cartOptions[2])) { //go back to main menu


                                } else if (cartResponse.equalsIgnoreCase(cartOptions[3])) { //view cart
                                    ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                                    Object object = oi.readObject();
                                    ArrayList<String> temp = (ArrayList<String>) object;
                                    ArrayList<String> allProducts = temp;
                                    String[] completeList = new String[allProducts.size()];
                                    allProducts.toArray(completeList);

                                    if (allProducts.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Your cart is empty", "Market", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        String trash = (String) JOptionPane.showInputDialog(null, "Here are the items in your cart:", "Market", JOptionPane.INFORMATION_MESSAGE, null, completeList, completeList[0]);

                                    }

                                }
                            }


                        } else if (buyerFirstResponse.equals((buyerOptions[5]))) { //export history
                            String filePath = JOptionPane.showInputDialog(null, "Enter the file path:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(filePath); //sender filepath
                            send.flush();

                            String confirmExport = receive.readLine();
                            if (confirmExport.equalsIgnoreCase("y")) {
                                JOptionPane.showMessageDialog(null, "File Was Exported!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            } else if (confirmExport.equalsIgnoreCase("n")) {
                                JOptionPane.showMessageDialog(null, "File Failed to Export!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            }

                        } else if (buyerFirstResponse.equals((buyerOptions[6]))) {
                            JOptionPane.showMessageDialog(null, "Thank you for using the Market!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            whileBuying = true;
                        } else if (buyerFirstResponse.equalsIgnoreCase(buyerOptions[7])) {
                            String storeName = (String) JOptionPane.showInputDialog(null, "Enter the name of the store that carries the product:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String productName = (String) JOptionPane.showInputDialog(null, "Enter the name of the product:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(storeName);   //sender details
                            send.flush();
                            send.println(productName);
                            send.flush();

                            String confirm = receive.readLine(); //confirm details
                            String finalConfirm = confirm.replaceAll(";", "\n");

                            JOptionPane.showMessageDialog(null, finalConfirm, "Market", JOptionPane.INFORMATION_MESSAGE);

                        } else if (buyerFirstResponse.equalsIgnoreCase(buyerOptions[8])) {
                            String searchTerm = (String) JOptionPane.showInputDialog(null, "Search:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(searchTerm);
                            send.flush();
                            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                            Object object = oi.readObject();
                            ArrayList<String> temp = (ArrayList<String>) object;
                            ArrayList<String> searchProducts = new ArrayList<>();
                            searchProducts.addAll(temp);
                            String[] completeList = new String[searchProducts.size()];
                            searchProducts.toArray(completeList);
                            String searchResponse = (String) JOptionPane.showInputDialog(null, "Here are the results:", "Market", JOptionPane.INFORMATION_MESSAGE, null, completeList, completeList[0]);


                        }
                    } while (whileBuying == false);


                }  //END OF BUYER


                //START OF SELLER
                if (theUserAccountType == 2) {
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
                            String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String productName = JOptionPane.showInputDialog(null, "Enter the product name:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String description = JOptionPane.showInputDialog(null, "Enter the product description:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String quantity = JOptionPane.showInputDialog(null, "Enter the quantity:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String price = JOptionPane.showInputDialog(null, "Enter the price:", "Market", JOptionPane.INFORMATION_MESSAGE);

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
                            String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store that the product is listed:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String productName = JOptionPane.showInputDialog(null, "Enter the product name:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String newStoreName = JOptionPane.showInputDialog(null, "Enter the new Store Name:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String newProductName = JOptionPane.showInputDialog(null, "Enter the new Product Name:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String newDescription = JOptionPane.showInputDialog(null, "Enter the new Description:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String newQuantity = JOptionPane.showInputDialog(null, "Enter the new Quantity:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String newPrice = JOptionPane.showInputDialog(null, "Enter the new price:", "Market", JOptionPane.INFORMATION_MESSAGE);
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
                            String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store that the product is listed:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            String productName = JOptionPane.showInputDialog(null, "Enter the name of the product:", "Market", JOptionPane.INFORMATION_MESSAGE);
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

                        } else if (sellerFirstResponse.equals(sellerOptions[5])) { //import file
                            String pathName = JOptionPane.showInputDialog(null, "Enter the path name of the file:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(pathName);
                            send.flush();//sender path name

                            String confirm = receive.readLine();

                            if (confirm.equalsIgnoreCase("y")) {
                                JOptionPane.showMessageDialog(null, "File was successfully imported!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirm.equalsIgnoreCase("n")) {
                                JOptionPane.showMessageDialog(null, "File failed to import!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else if (sellerFirstResponse.equals(sellerOptions[6])) { //export file
                            String pathName = JOptionPane.showInputDialog(null, "Enter the desired path name:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(pathName);
                            send.flush();

                            String confirm = receive.readLine();
                            if (confirm.equalsIgnoreCase("y")) {
                                JOptionPane.showMessageDialog(null, "File was successfully exported!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            } else if (confirm.equalsIgnoreCase("n")) {
                                JOptionPane.showMessageDialog(null, "File failed to export!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            }


                        } else if (sellerFirstResponse.equals(sellerOptions[7])) { //log out
                            JOptionPane.showMessageDialog(null, "Thank you for using the Market!", "Market", JOptionPane.INFORMATION_MESSAGE);
                            whileSelling = true;
                        } else if (sellerFirstResponse.equals(sellerOptions[8])) { //seller log
                            String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store that you wish to see the statistics for:", "Market", JOptionPane.INFORMATION_MESSAGE);
                            send.println(storeName);
                            send.flush();

                            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                            Object object = oi.readObject();
                            ArrayList<String> temp = (ArrayList<String>) object;
                            ArrayList<String> logItems = new ArrayList<>();


                            for (int i = 0; i < temp.size(); i++) {
                                logItems.add(temp.get(i));
                            }
                            if (logItems.size() > 0) {
                                String[] completeList = new String[logItems.size()];
                                logItems.toArray(completeList);
                                sellerFirstResponse = (String) JOptionPane.showInputDialog(null, "Statistics", "Market", JOptionPane.INFORMATION_MESSAGE, null, completeList, completeList[0]);

                            } else {
                                String[] completeList = new String[1];
                                completeList[1] = "n";
                                JOptionPane.showMessageDialog(null, "No statistics to show!", "Market", JOptionPane.INFORMATION_MESSAGE);

                            }

                        } else if (sellerFirstResponse.equalsIgnoreCase(sellerOptions[9])) {
                            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                            Object object = oi.readObject();
                            ArrayList<String> temp = (ArrayList<String>) object;
                            ArrayList<String> cartStats = new ArrayList<>();


                            cartStats.addAll(temp);
                            String[] finalStats = new String[cartStats.size()];
                            cartStats.toArray(finalStats);


                            String selectNull = (String) JOptionPane.showInputDialog(null, "Statistics", "Market", JOptionPane.INFORMATION_MESSAGE, null, finalStats, finalStats[0]);
                        }

                    } while (whileSelling == false);
                }
            }


        } catch (Exception e) {
            String simon = "Says do nothing";
        }


    }
}
