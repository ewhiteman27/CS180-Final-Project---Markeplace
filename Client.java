import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import java.util.ArrayList;

/**
 * A Client class
 * It handles everything that deals with the GUI and sends information to the server
 * @authors saujinpark park1485 and
 * @version 1.0
 */
public class Client {

    public static void main(String[] args) throws IOException {
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
                JOptionPane.showMessageDialog(null, "Welcome to the Purdue Marketplace!", "Market", JOptionPane.INFORMATION_MESSAGE);
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
                    do {

                        String username = (String) JOptionPane.showInputDialog(null, "Please enter a Username", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(username);
                        send.flush();

                        String password = (String) JOptionPane.showInputDialog(null, "Please enter a Password", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(password);
                        send.flush();

                        String email = (String) JOptionPane.showInputDialog(null, "Please enter a Email", "Market", JOptionPane.INFORMATION_MESSAGE);
                        send.println(email);
                        send.flush();

                        String buyerSeller = (String) JOptionPane.showInputDialog(null, "Will you be using this application as a seller or a buyer?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buySellOption, buySellOption[0]);
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



        } catch (Exception e) {
            throw e;
        }


    }
}
