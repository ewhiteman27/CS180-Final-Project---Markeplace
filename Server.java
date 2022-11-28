import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * A SearchServer class
 * It handles everything that deals with processing the search results. It also stores the data the search results are from.
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
        if (choice.equals(createLogIn[0])) {
            //Verifying that creating an account is successful
            String username = receive.readLine();


            String password = receive.readLine();

            String email = receive.readLine();

            String buyerSeller = receive.readLine();

            if (buyerSeller.equalsIgnoreCase("buyer")) {
                user = user.createAccount(username, email, password, 1);
                accountCreated = true;
                totalUsers.add(user);
            } else if (buyerSeller.equalsIgnoreCase("seller")) {
                user = user.createAccount(username, email, password, 2);
                accountCreated = true;
                totalUsers.add(user);
            }


        } else if (choice.equals(createLogIn[1])) {


        }



    }
}
