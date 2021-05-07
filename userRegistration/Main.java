import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Main {

    private static UserService userService = new UserService();

    private static final String INITIAL_PROMPT = "Please select an option:" + "\n 1. Login" + "\n 2. Register"
            + "\n 3. Exit";

    private static final String LOGGED_IN_PROMPT = "Please select an option:" + "\n 1. Show email"
            + "\n 2. Reset password" + "\n 3. Logout";

    private static User login(Scanner sc) {
        System.out.print("Enter your email: ");
        String email = sc.next();
        System.out.print("Enter your password: ");
        String password = sc.next();
        try {
            User user = userService.login(email, password);
            System.out.println("Login Successful");
            return user;
        } catch (LoginException e) {
            System.out.println("Login Failed: " + e.getMessage());
            return null;
        }
    }

    private static User register(Scanner sc) {
        System.out.print("Enter you name: ");
        String name = sc.next();
        System.out.print("Enter your email: ");
        String email = sc.next();
        System.out.print("Enter your password: ");
        String password = sc.next();
        User user = new User(name, email, password);
        try {
            userService.registerUser(user);
            System.out.println("Registration Successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private static void resetPassword(Scanner sc, User user) {
        System.out.print("Enter your current password: ");
        String currentPassword = sc.next();
        if(user.getPassword().equals(currentPassword)) {
            System.out.print("Enter your new password: ");
            String newPassword = sc.next();
            user.setPassword(newPassword);
            System.out.println("Password successfully updated");
        } else {
            System.out.println("Incorrect Password, Please try again");
        }
    }

    public static void main(String[] args) {
        User loggedInUser = null;
        Scanner sc = new Scanner(System.in);

        while(true) {
            if(loggedInUser != null) {
                System.out.println(LOGGED_IN_PROMPT);
                int request = sc.nextInt();
                switch(request) {
                    case 1:
                        System.out.println("Your email is: " + loggedInUser.getEmail());
                        break;
                    case 2:
                        resetPassword(sc, loggedInUser);
                        break;
                    case 3:
                        loggedInUser = null;
                        System.out.println("Successfully Logged Out");
                        break;
                    default:
                        System.out.println("Invalid selection please try again");
                }
            } else {
                System.out.println(INITIAL_PROMPT);
                int request = sc.nextInt();
                switch(request) {
                    case 1:
                        loggedInUser = login(sc);
                        break;
                    case 2:
                        loggedInUser = register(sc);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid selection please try again");
                }
            }
        }
    }
}