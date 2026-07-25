import java.util.Scanner;

public class STAGE1 {
    // Return the current balance without changing it.
    static double showBalance(double balance) {
        return balance;
    }

    // Add money to the balance only when the deposit amount is valid.
    // Invalid values such as zero or a negative number are ignored.
    static double deposit(double amount, double balance) {
        if (amount <= 0) {
            return balance;
        }
        return balance + amount;
    }

    // Remove money from the balance only when the withdrawal amount is valid.
    // The amount must be positive and cannot exceed the current balance.
    static double withdrawal(double amount, double balance) {
        if (amount <= 0 || amount > balance) {
            return balance;
        }
        return balance - amount;
    }

    public static void main(String[] args) {
        // Start with a zero balance.
        double balance = 0;

        // Scanner lets us read user input from the keyboard.
        try (Scanner scanner = new Scanner(System.in)) {
            int option;

            // Keep showing the menu until the user chooses to exit.
            do {
                // Display the available banking options to the user.
                System.out.print("""
                    ### Select a menu ####
                    1) Show balance:
                    2) Deposit:
                    3) Withdraw:
                    4) Exit:
                    """);

                // Read the menu choice entered by the user.
                option = scanner.nextInt();

                // Handle the chosen option using a switch statement.
                switch (option) {
                    case 1 -> System.out.printf("Current balance is: %.2f$\n", showBalance(balance));
                    case 2 -> {
                        System.out.print("Enter deposit amount: ");
                        double amount = scanner.nextDouble();

                        // Make sure the deposit amount is valid before updating the balance.
                        if (amount <= 0) {
                            System.out.print("Please input a valid amount");
                        } else {
                            balance = deposit(amount, balance);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();

                        // Make sure the withdrawal amount is valid and that funds are available.
                        if (amount <= 0) {
                            System.out.print("Please input a valid amount");
                        } else if (amount > balance) {
                            System.out.print("Not enough balance");
                        } else {
                            balance = withdrawal(amount, balance);
                        }
                    }
                    case 4 -> {
                        // Exit the program and say goodbye.
                        System.out.println("Exiting...");
                        System.out.println("Thanks for using our Banking App");
                    }
                    default -> System.out.println("Invalid option");
                }
            } while (option != 4);
        }
    }
}
