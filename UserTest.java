public class UserTest {
    public static void main(String[] args) {
        try {
            User u = new User();
            // this should work first time, no other time
            u = u.createAccount("user", "user@email.domain", "password123", 2);
            //use this after first test
            u = u.logIn("user" , "password123");
            User u2 = new User();
            u2.logIn("username12345" , "pass"); // this shouldnt work
            // should print true
            System.out.println(u.changePassword("password" , "user" , "user@email.domain"));
            // should print false
            System.out.println(u.changeUsername("user" , "password" , "user@email.domain"));
            // should work
            u2 = u2.createAccount("username12345" , "username@email.com" , "password" , 1);
            // should print true
            System.out.println(u2.deleteAccount("username12345" , "password"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
