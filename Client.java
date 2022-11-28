import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import java.util.ArrayList;

/**
 * A SearchClient class
 * It handles everything that deals with the GUI the user sees
 * @author saujinpark park1485
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
        try {
            JOptionPane.showMessageDialog(null, "Welcome to the Purdue Marketplace!", "Market", JOptionPane.INFORMATION_MESSAGE);
            String choice = (String) JOptionPane.showInputDialog(null,
                    "Please select one of the available options", "Market",
                    JOptionPane.QUESTION_MESSAGE, null, createLogIn, createLogIn[0]);
            send.write(choice);
            send.println();
            send.flush();

            if (choice.equals(createLogIn[0])) {
                String[] buySellOption = new String[2];
                buySellOption[0] = "Buyer";
                buySellOption[1] = "Seller";

                String username = (String) JOptionPane.showInputDialog(null, "Please enter a Username", "Market", JOptionPane.INFORMATION_MESSAGE);
                send.write(username);
                send.println();
                send.flush();

                String password = (String) JOptionPane.showInputDialog(null, "Please enter a Password", "Market", JOptionPane.INFORMATION_MESSAGE);
                send.write(password);
                send.println();
                send.flush();

                String email = (String) JOptionPane.showInputDialog(null, "Please enter a Email", "Market", JOptionPane.INFORMATION_MESSAGE);
                send.write(email);
                send.println();
                send.flush();

                String buyerSeller = (String) JOptionPane.showInputDialog(null, "Will you be using this application as a seller or a buyer?", "Market", JOptionPane.INFORMATION_MESSAGE, null, buySellOption, buySellOption[0]);
                send.write(buyerSeller);
                send.println();
                send.flush();




            } else {

            }



        } catch (Exception e) {
            throw e;
        }


    }
}

