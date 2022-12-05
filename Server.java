import javax.swing.*;
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
        //end of necessary things

        boolean logInMenu = false;
        do {
            String choice = receive.readLine(); //reads if user creates an account or logs in
            User user = new User();
            boolean hasTheAccountBeenCreated = false;
            if (choice.equals(createLogIn[0])) {
                //Verifying that creating an account is successful
                do {

                    String username = receive.readLine();

                    String password = receive.readLine();

                    String email = receive.readLine();

                    String buyerSeller = receive.readLine();

                    //Receiver 01


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
                            hasTheAccountBeenCreated = true;
                        } catch (Exception e) {
                            String xVariable = "x";
                            send.println(xVariable);
                            send.flush();
                        }
                    }
                } while (hasTheAccountBeenCreated == false);

                //end of account creation


            } else if (choice.equals(createLogIn[1])) { //LOGGING IN
                boolean hasLoggedIn = false;

                String confirmLogIn = receive.readLine();
                //receiver 02

                if (confirmLogIn.equalsIgnoreCase("1")) {
                    logInMenu = false;
                } else {
                    logInMenu = true;
                    do {
                        String username = receive.readLine();

                        String password = receive.readLine();
                        //Receiver 03

                        try {

                            user = user.logIn(username, password);
                            edits = user;
                            hasLoggedIn = true;
                            String yOrn = "y";
                            send.println(yOrn);
                            send.flush();
                            hasLoggedIn = true;
                            send.println(user.getAccountType());
                            send.flush();

                        } catch (Exception e) { //exception is caught when user fails to log in

                            String yOrn = "n";
                            send.println(yOrn);
                            send.flush();
                        }

                    } while (hasLoggedIn == false);
                }


            }
        } while (logInMenu == false);
        //end of login portion




        String userAccountType = receive.readLine(); //checks to see if the user is a buyer or seller
        //receive 04

        if (userAccountType.equalsIgnoreCase("1")) {  //If user is a buyer
            String[] buyerOptions = new String[7];
            buyerOptions[0] = "View All Available Products";
            buyerOptions[1] = "Sort The Marketplace";
            buyerOptions[2] = "Edit Account";
            buyerOptions[3] = "Delete Account";
            buyerOptions[4] = "View Cart";
            buyerOptions[5] = "View Purchase History";
            buyerOptions[6] = "Log Out";
            boolean whileBuying = false;

            do {
                String buyerFirstResponse = receive.readLine();
                if (buyerFirstResponse.equals(buyerOptions[0])) {


                } else if (buyerFirstResponse.equals((buyerOptions[1]))) {


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


                    }

                } else if (buyerFirstResponse.equals((buyerOptions[3]))) {

                } else if (buyerFirstResponse.equals((buyerOptions[4]))) {

                } else if (buyerFirstResponse.equals((buyerOptions[5]))) {

                } else if (buyerFirstResponse.equals((buyerOptions[6]))) {
                    whileBuying = true;
                }
            } while (whileBuying == false);











        } else if (userAccountType.equalsIgnoreCase("2")) { //If user is a seller



        }

    }

}
