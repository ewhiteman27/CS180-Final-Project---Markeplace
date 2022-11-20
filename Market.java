import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* Market
*
* This class provides the ability for the user to access the different options the Purdue market offers
*
* @author Amit Manchella, 
*
* @version November 13, 2022
*/


public class Market {

    private static final String Welcome = "Welcome to the Purdue Marketplace! Here you will be able to buy and sell" +
            " goods of your choosing.";
    private static final String existingAccountQuestion = "Would you like to create an account?(yes/no)";
    private static final String cancel = "Thank you for using the Purdue Marketplace!";
    private static final String UserPass = "Please enter your username, password, and email: (Case sensitive)";
    private static final String Username = "Username:";
    private static final String Password = "Password:";

    private static final String Email = "Email:";

    public static void main(String[] args) {
        ArrayList<Object> totalUsers = new ArrayList<>(); //ArrayList of all the existing users
        int buyerSellerInt = 0;
        boolean accountCreated = false;
        boolean removeAccount = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println(Welcome);
        String existingAccountAnswer = "";
        boolean answer = false;
        while (!answer) {
            System.out.println(existingAccountQuestion);
            existingAccountAnswer = scanner.nextLine();
            if (existingAccountAnswer.equalsIgnoreCase("yes") ||
                    existingAccountAnswer.equalsIgnoreCase("no")) {
                answer = true;
            }
        }
        answer = false;
        User user = new User();
        if (existingAccountAnswer.equalsIgnoreCase("yes")) {
            do {
                try {
                    System.out.println(UserPass);
                    System.out.println(Username);
                    String usernameAnswer = scanner.nextLine();
                    System.out.println(Password);
                    String passwordAnswer = scanner.nextLine();
                    System.out.println(Email);
                    String emailAnswer = scanner.nextLine();
                    System.out.println("Would you like to be a buyer or seller?(buyer/seller)");
                    String buyerSeller = scanner.nextLine();
                    if (buyerSeller.equalsIgnoreCase("buyer")) {
                        buyerSellerInt = 1;
                        user = user.createAccount(usernameAnswer, emailAnswer, passwordAnswer, buyerSellerInt);
                        accountCreated = true;
                        totalUsers.add(user);
                    } else if (buyerSeller.equalsIgnoreCase("seller")) {
                        buyerSellerInt = 2;
                        user = user.createAccount(usernameAnswer, emailAnswer, passwordAnswer, buyerSellerInt);
                        accountCreated = true;
                        totalUsers.add(user);
                    } else {
                        System.out.println("Please choose to be a buyer or seller!");
                        accountCreated = false;
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please try again!");
                    accountCreated = false;
                }
            } while (!accountCreated);
        } else if (existingAccountAnswer.equalsIgnoreCase("no")) {
            do {
                String logInAnswer = "";
                while (!answer) {
                    System.out.println("Would you like to log in? (yes/no)");
                    logInAnswer = scanner.nextLine();
                    if (logInAnswer.equalsIgnoreCase("yes") ||
                            logInAnswer.equalsIgnoreCase("no")) {
                        answer = true;
                    }
                }
                answer = false;

                if (logInAnswer.equalsIgnoreCase("yes")) {
                    do {
                        try {
                            accountCreated = true;
                            System.out.println("Please enter your username and password:");
                            System.out.println(Username);
                            String usernameAnswer = scanner.nextLine();
                            System.out.println(Password);
                            String passwordAnswer = scanner.nextLine();
                            user = user.logIn(usernameAnswer, passwordAnswer);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please try again!");
                            accountCreated = false;
                        }
                    } while (!accountCreated);

                } else if (logInAnswer.equalsIgnoreCase("no")) {
                    accountCreated = false;
                    System.out.println(cancel);
                    break;
                } else {
                    System.out.println("Please enter yes or no!");
                    accountCreated = false;
                }
            } while (!accountCreated);
        }

        if (accountCreated) {
            System.out.println("Log in successful!");

            boolean intoAccountOptions = false;
            boolean changeUsername = false;
            boolean changePassword = false;
            boolean changeEmailBoolean = false;


            do {
                int firstIntAnswer = -1;
                while (firstIntAnswer == -1) {
                    System.out.println("Would you like to 1. delete your account " +
                            "2. change account details or 3. continue? (1, 2, 3)");
                    if (scanner.hasNextInt()) {
                        firstIntAnswer = scanner.nextInt();
                        if (firstIntAnswer < 1 || firstIntAnswer > 3) {
                            firstIntAnswer = -1;
                        }
                    }
                    scanner.nextLine();
                }

                if (firstIntAnswer == 1) {
                    System.out.println(Username);
                    String usernameAnswer = scanner.nextLine();
                    System.out.println(Password);
                    String passwordAnswer = scanner.nextLine();
                    removeAccount = user.deleteAccount(usernameAnswer, passwordAnswer);
                    if (removeAccount) {
                        continue;
                    } else if (!removeAccount) {
                        System.out.println("Account was not deleted! Either username or password was incorrect.");
                        intoAccountOptions = true;
                    }


                } else if (firstIntAnswer == 2) {
                    System.out.println("Would you like to change your 1. username 2. password or 3. email? (1, 2, 3)");
                    int changeInt = -1;
                    while (changeInt == -1) {
                        if (scanner.hasNextInt()) {
                            changeInt = scanner.nextInt();
                            if (changeInt < 1 || changeInt > 3) {
                                changeInt = -1;
                            }
                        }
                        scanner.nextLine();
                    }
                    if (changeInt == 1) {
                        System.out.println("To change the username we must verify your email and password");
                        System.out.println(Email);
                        String emailAnswer = scanner.nextLine();
                        System.out.println(Password);
                        String passwordAnswer = scanner.nextLine();
                        System.out.println("What would you like your new username to be?");
                        System.out.println(Username);
                        String usernameAnswer = scanner.nextLine();
                        changeUsername = user.changeUsername(usernameAnswer, passwordAnswer, emailAnswer);
                        if (changeUsername) {
                            continue;
                        } else {
                            System.out.println("Username was not changed! Either email or password was incorrect, " +
                                    "or the username you selected was already taken.");
                            intoAccountOptions = true;
                        }
                    } else if (changeInt == 2) {
                        System.out.println("To change the password we must verify your email and username");
                        System.out.println(Email);
                        String emailAnswer = scanner.nextLine();
                        System.out.println(Username);
                        String usernameAnswer = scanner.nextLine();
                        System.out.println("What would you like your new password to be?");
                        System.out.println(Password);
                        String passwordAnswer = scanner.nextLine();
                        try {
                            changePassword = user.changePassword(passwordAnswer, usernameAnswer, emailAnswer);
                            if (changePassword) {
                                continue;
                            } else if (!changePassword) {
                                System.out.println("Password was not changed! Either email or username was incorrect.");
                                intoAccountOptions = true;
                            }
                        } catch (InvalidPasswordException e) {
                            System.out.println(e.getMessage());
                            intoAccountOptions = true;
                        }

                    } else if (changeInt == 3) {
                        System.out.println("To change the email we must verify your username and password");
                        System.out.println(Username);
                        String usernameAnswer = scanner.nextLine();
                        System.out.println(Password);
                        String passwordAnswer = scanner.nextLine();
                        System.out.println("What would you like your new email to be?");
                        System.out.println(Email);
                        String emailAnswer = scanner.nextLine();
                        changeEmailBoolean = user.changeEmail(emailAnswer, usernameAnswer, passwordAnswer);
                        if (changeEmailBoolean) {
                            continue;
                        } else if (!changeEmailBoolean) {
                            System.out.println("Email was not changed! Either username or password was incorrect," +
                                    " or the email is already in use.");
                            intoAccountOptions = true;
                        }

                    } else {
                        System.out.println("Invalid Response!");
                        System.out.println("Please enter either 1, 2, or 3.");
                        intoAccountOptions = true;
                    }

                } else if (firstIntAnswer == 3) {
                    Product product = new Product();
                    if (user.getAccountType() == 1) {
                        boolean loopingBuyer = true;
                        Buyer buyer = new Buyer(user.getUsername());
                        System.out.println("Welcome Buyer!");
                        System.out.println("Here are all of the available products:");
                        try {
                            ArrayList<String> allProducts = buyer.getAllProducts();
                            for (int i = 0; i < allProducts.size(); i++) {
                                System.out.println(allProducts.get(i));
                            }
                        } catch (Exception e) {
                            System.out.println("No products have been listed");
                        }
                        System.out.println("The format for the listings are Sellerusername,Storename,Productname,Quantity,Price");


                        do {
                            System.out.println("Would you like to" + "\n" + "1.View all products" + "\n" + "2.Search for a specific product" + "\n" + "3.Sort the " +
                                    "marketplace" + "\n" + "4.View cart" + "\n" + "5.Check out" + "\n" + "6.Add an item to the cart" + "\n" + "7.Remove " +
                                    "an item from the cart" +
                                    "\n" + "8.View purchase history" + "\n" + "9.View details of a product" + "\n" + "10.Export" +
                                    " purchase history" + "\n" + "11.Log out");
                            String optionsOfInitialBuyer = scanner.nextLine();

                            if (optionsOfInitialBuyer.equals("2")) {

                                System.out.println("Which product would you like to search for?"); //Searching for a certain item
                                String search = scanner.nextLine();
                                boolean goSearch = true;
                                do {
                                    try {
                                        buyer.findProduct(search);
                                        if (buyer.findProduct(search).isEmpty()) {
                                            System.out.println("There were no matching results");
                                            System.out.println("Would you like to look for something else?");
                                        } else {
                                            for (int i = 0; i < buyer.findProduct(search).size(); i++) {
                                                System.out.println(buyer.findProduct(search).get(i));
                                            }
                                            goSearch = false;
                                        }
                                    } catch (IOException ignored) {

                                    }
                                } while (goSearch);
                            } else if (optionsOfInitialBuyer.equals("6")) {
                                System.out.println("Would you like to add an item to the cart? yes/no");
                                String cartPrompt = scanner.nextLine();
                                if (cartPrompt.equalsIgnoreCase("yes")) {
                                    boolean cartAdd;
                                    do {
                                        System.out.println("Product Store Name:");
                                        String productStoreName = scanner.nextLine();
                                        System.out.println("Username of Seller:");
                                        String sellerName = scanner.nextLine();
                                        System.out.println("Product Name:");
                                        String productName = scanner.nextLine();
                                        System.out.println("Product Price:");
                                        String productPrice = scanner.nextLine();
                                        System.out.println("Product Quantity:");
                                        String productQuantity = scanner.nextLine();

                                        try {
                                            buyer.addToCart(sellerName, productStoreName, productName, productPrice,
                                                    productQuantity);
                                        } catch (Exception e) {
                                            System.out.println("An error occurred. Please try again.");
                                            cartAdd = true;
                                        }
                                        System.out.println("Are there any more items you would like to add?(yes/no)");
                                        String addMoreItems = scanner.nextLine();
                                        cartAdd = addMoreItems.equalsIgnoreCase("yes");
                                        if (addMoreItems.equalsIgnoreCase("no")) {
                                            cartAdd = false;
                                        }
                                    } while (cartAdd);
                                }

                            } else if (optionsOfInitialBuyer.equals("7")) {
                                System.out.println("Would you like to remove an item from the cart? yes/no");
                                String removePrompt = scanner.nextLine();

                                if (removePrompt.equalsIgnoreCase("yes")) {
                                    boolean removing = true;
                                    while (removing) {
                                        System.out.println("Enter product name: (Case Sensitive)");
                                        String productName = scanner.nextLine();
                                        try {
                                            buyer.removeFromCart(productName);
                                            System.out.println("Are there any other items you would like to remove? yes/no");
                                            String removeAgain = scanner.nextLine();
                                            if (removeAgain.equalsIgnoreCase("yes")) {
                                                removing = true;
                                            } else {
                                                removing = false;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("That product is not in the cart");
                                            removing = false;
                                        }
                                    }

                                }

                            } else if (optionsOfInitialBuyer.equals("5")) {
                                boolean pay;

                                do {
                                    System.out.println("Please enter the first four digits of your credit card");
                                    int firstFour = scanner.nextInt();
                                    System.out.println("Please enter the second four digits of your credit card");
                                    int secondFour = scanner.nextInt();
                                    System.out.println("Please enter the third four digits of your credit card");
                                    int thirdFour = scanner.nextInt();
                                    System.out.println("Please enter the final four digits of your credit card");
                                    int fourthFour = scanner.nextInt();
                                    String creditCardNumber = "";
                                    creditCardNumber = firstFour + " " + secondFour + " " + thirdFour + " " + fourthFour;
                                    try {
                                        buyer.verifyCard(creditCardNumber);
                                        pay = false;
                                    } catch (Exception e) {
                                        System.out.println("Error. Invalid Credit Card Number.");
                                        System.out.println("Would you like to try again? yes/no");
                                        String failedPay = scanner.nextLine();
                                        if (failedPay.equalsIgnoreCase("yes")) {
                                            pay = true;
                                        } else {
                                            pay = false;
                                        }
                                    }

                                } while (pay);
                            } else if (optionsOfInitialBuyer.equals("9")) {
                                boolean goDetails = true;
                                System.out.println("Would you like to view the details of a product? yes/no");
                                String confirm = scanner.nextLine();
                                if (confirm.equalsIgnoreCase("yes")) {
                                    while (goDetails) {
                                        try {
                                            System.out.println("Enter the username of the seller:");
                                            String sellName = scanner.nextLine();
                                            System.out.println("Enter the name of the store:");
                                            String storeName = scanner.nextLine();
                                            System.out.println("Enter the name of the product:");
                                            String productName = scanner.nextLine();
                                            if (buyer.getDetails(sellName, storeName, productName).get(0) != null) {
                                                System.out.println(buyer.getDetails(sellName, storeName, productName).get(0));
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Product could not be found");
                                            System.out.println("Would you like to try again? yes/no");
                                            String againPrompt = scanner.nextLine();
                                            if (againPrompt.equalsIgnoreCase("no")) {
                                                goDetails = false;
                                            }
                                        }
                                    }
                                }
                            } else if (optionsOfInitialBuyer.equals("10")) {
                                boolean download = true;
                                System.out.println("Enter the pathname you want to download the file to");
                                String path = scanner.nextLine();
                                while (download) {
                                    try {
                                        buyer.exportHistory(path);
                                        download = false;
                                    } catch (Exception e) {
                                        System.out.println("Invalid pathname");
                                        System.out.println("Would you like to try again? yes/no");
                                        String downloadAgain = scanner.nextLine();
                                        if (downloadAgain.equalsIgnoreCase("yes")) {
                                            download = true;
                                        } else {
                                            download = false;
                                        }
                                    }
                                }

                            } else if (optionsOfInitialBuyer.equals("3")) {
                                System.out.println("In which way would you like to sort the marketplace?");
                                System.out.println("1.Price" + "\n" + "2.Quantity" + "\n" + "3.Stop"); // this needs to be worked on
                                String buyerOptionsSorting = scanner.nextLine();
                                if (buyerOptionsSorting.equals("1")) {
                                    try {
                                        if (buyer.sortPrice().get(0) != null) {
                                            for (int i = 0; i < buyer.sortPrice().size(); i++) {
                                                System.out.println(buyer.sortPrice().get(i));
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("There are no products listed");
                                    }
                                } else if (buyerOptionsSorting.equals("2")) {
                                    try {
                                        if (buyer.sortQuantity().get(0) != null) {
                                            for (int i = 0; i < buyer.sortQuantity().size(); i++) {
                                                System.out.println(buyer.sortQuantity().get(i));
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("There are no products listed");
                                    }
                                }
                            } else if (optionsOfInitialBuyer.equals("1")) {
                                System.out.println("Here are all of the available products:");
                                try {
                                    for (int i = 0; i < buyer.getAllProducts().size(); i++) {
                                        System.out.println(buyer.getAllProducts().get(i));
                                    }
                                } catch (Exception e) {
                                    System.out.println("No products have been listed");
                                }

                            } else if (optionsOfInitialBuyer.equals("4")) {
                                System.out.println("Cart");
                                if (!buyer.getCart().isEmpty()) {
                                    for (int i = 0; i < buyer.getCart().size(); i++) {
                                        System.out.println(buyer.getCart().get(i));
                                    }
                                } else {
                                    System.out.println("No items in cart");
                                }
                            } else if (optionsOfInitialBuyer.equals("11")) {
                                loopingBuyer = false;
                                intoAccountOptions = false;

                            } else {
                                System.out.println("Please enter a valid number");
                                loopingBuyer = true;
                            }

                        } while (loopingBuyer);

                        //end of things the buyer implements


                    } else if (user.getAccountType() == 2) {// start doing the sell stuff
                        Seller seller = new Seller(user.getUsername());
                        System.out.println("Welcome Seller!");
                        String sellerOptions = "";
                        boolean sellerOptionsBoolean = false;
                        boolean sellEditDelete = false;
                        do {
                            System.out.println("Would you like to create a product, edit a product, " +
                                    "or delete a product, export a product to file, or log out? " +
                                    "(create, edit, delete, export, logout)");
                            sellerOptions = scanner.nextLine();

                            if (sellerOptions.equalsIgnoreCase("create")) {
                                System.out.println("Would you like to add the product from a file? (yes/no)");
                                String file = scanner.nextLine();
                                if (file.equalsIgnoreCase("yes")) {
                                    System.out.println("Enter the path of the file to read from");
                                    System.out.println("The correct format is: [Store Name]," +
                                            "[Product Name],[Product Description],[Product Quantity],[Product Price]");
                                    System.out.println("Example: Target,Ipad,Apple Device,10,300");
                                    String filepath = scanner.nextLine();
                                    boolean readFromFile = seller.importUserFile(user.getUsername(), filepath);

                                    if (!readFromFile) {
                                        System.out.println("the product could not be created");
                                    }
                                } else {

                                    System.out.println("Store Name:");
                                    String storeName = scanner.nextLine();
                                    System.out.println("Product Name:");
                                    String productName = scanner.nextLine();
                                    System.out.println("Product Description:");
                                    String productDescription = scanner.nextLine();
                                    System.out.println("Product Quantity:");
                                    int productQuantity = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Product Price:");
                                    double productPrice = scanner.nextDouble();
                                    scanner.nextLine();
                                    sellEditDelete = seller.createProduct(productName, storeName, productDescription, productQuantity,
                                            productPrice, user.getUsername());
                                    if (!sellEditDelete) {
                                        System.out.println("The product could not be created");
                                    }
                                }
                            } else if (sellerOptions.equalsIgnoreCase("edit")) {
                                System.out.println("Product Name:");
                                String productName = scanner.nextLine();
                                System.out.println("Store Name:");
                                String storeName = scanner.nextLine();
                                System.out.println("Product Description:");
                                String productDescription = scanner.nextLine();
                                System.out.println("Product Quantity:");
                                int productQuantity = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Product Price:");
                                double productPrice = scanner.nextDouble();
                                scanner.nextLine();
                                sellEditDelete = seller.editProduct(productName, storeName, productDescription, productQuantity,
                                        productPrice, user.getUsername());
                                if (!sellEditDelete) {
                                    System.out.println("The product could not be edited");
                                }

                            } else if (sellerOptions.equalsIgnoreCase("delete")) {
                                System.out.println("Product Name:");
                                String productName = scanner.nextLine();
                                System.out.println("Store Name:");
                                String storeName = scanner.nextLine();
                                System.out.println("Product Description:");
                                String productDescription = scanner.nextLine();
                                System.out.println("Product Quantity:");
                                int productQuantity = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Product Price:");
                                double productPrice = scanner.nextDouble();
                                scanner.nextLine();
                                sellEditDelete = seller.deleteProduct(productName, storeName, productDescription, productQuantity,
                                        productPrice, user.getUsername());
                                if (!sellEditDelete) {
                                    System.out.println("The product could not be deleted");
                                }

                            } else if (sellerOptions.equalsIgnoreCase("export")) {
                                System.out.println("Enter the file path to export to");
                                String filePathName = scanner.nextLine();
                                seller.exportUserFile(user.getUsername(), filePathName);
                            } else if (sellerOptions.equalsIgnoreCase("logout")) {
                                sellerOptionsBoolean = true;
                            } else {
                                System.out.println("Please enter a valid option");
                            }

                        } while (!sellerOptionsBoolean);
                        boolean sellerFinished = true;
                        do {
                            System.out.println("Would you like to see the dashboard or shopping cart\n" +
                                    "Enter 1 for dashboard\nEnter 2 for shopping cart\nEnter 3 for logout");
                            int sellerDecision = scanner.nextInt();
                            scanner.nextLine();
                            if (sellerDecision == 1) {
                                sellerFinished = false;
                            } else if (sellerDecision == 2) {
                                seller.sellerViewCustomerPurchase(user.getUsername());
                                sellerFinished = false;
                            } else if (sellerDecision == 3) {
                                sellerFinished = false;
                            } else {
                                System.out.println("Please enter a valid option");
                            }
                        } while (sellerFinished);
                    }

                }

            } while (intoAccountOptions);
            System.out.println(cancel);

        }
    }
}
