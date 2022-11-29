import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * A SearchServer class
 * It handles everything that deals with processing the search results. It also stores the data the search results are from.
 *
 * @author saujinpark
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









            } else if (choice.equals(createLogIn[1])) {


        }
    }

}

