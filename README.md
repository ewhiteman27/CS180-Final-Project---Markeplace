# CS180-Project-5

Compile and run this project by running the Server class, then the Client class, in IntelliJ or another Jave IDE.
Report submitted by Amit Manchella, Repository submitted by Ethan Whiteman

Classes:
Server - Main author: Saujin Park - Connects to Client class, receives information, processes inputs, and calls methods from the NewBuyer, NewSeller, NewProduct, and User classes before sending data back to the Client class.
Client - Main author: Saujin Park - Connects to Server class, receives inputs from users, sends information to Server, and displays options and information in GUIs for users to select from.
NewBuyer - Main author: Ethan Whiteman - Used by the Server class, contains all methods for Buyer type accounts such as adding and removing items in a cart, purchasing items, viewing available products, and leaving and viewing reviews on products.
NewSeller - Main author: Ethan Whiteman - Used by the Server class, contains all methods for Seller type accounts such as creating, editing, and deleting products, viewing items that have been purchased or are in a customer's cart, and importing or exporting files containing product information.
NewProduct - Main author: Ethan Whiteman - Superclass to NewBuyer and NewSeller classes. All methods which read from or write to a file are contained in this class, as well as methods which reformat products to be more readable in the GUI view.
Classes taken from Project 4: User, InvalidEmailException, InvalidLogInException, InvalidPasswordException, InvalidUsernameException
