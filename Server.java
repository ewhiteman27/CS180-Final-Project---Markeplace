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

    }

}
