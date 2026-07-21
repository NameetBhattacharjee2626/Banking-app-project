import java.util.Scanner;

public class STAGE1 {
    // This method shows the current balance to the user.
    static double showBalance(double balance) {
        return balance;
    }

    // This method adds money to the balance.
    // The amount must be positive to be valid.
    static double deposit(double amount, double balance) {
        balance += amount;
        return balance;
    }

    // This method removes money from the balance.
    // The amount must be positive and not more than the current balance.
    static double withdrawal(double amount, double balance) {
        balance -= amount;
        return balance;
    }

    public static void main(String[] args) {
        // Start with a zero balance.
        double balance = 0;

        // Scanner lets us read user input from the keyboard.
        try (Scanner scanner = new Scanner(System.in)) {
            int option;

            // Keep showing the menu until the user chooses to exit.
            do {
                System.out.print("""
                    ### Select a menu ####
                    1) Show balance:
                    2) Deposit:
                    3) Withdraw:
                    4) Exit:
                    """);

                // Read the menu choice entered by the user.
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> System.out.printf("Current balance is: %.2f$\n", showBalance(balance));
                    case 2 -> {
                        System.out.print("Enter deposit amount: ");
                        double amount = scanner.nextDouble();

                        // Make sure the deposit amount is valid.
                        if (amount <= 0) {
                            System.out.print("Please input a valid amount");
                        } else {
                            balance = deposit(amount, balance);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();

                        // Make sure the withdrawal amount is valid.
                        if (amount <= 0) {
                            System.out.print("Please input a valid amount");
                        } else if (amount > balance) {
                            System.out.print("Not enough balance");
                        } else {
                            balance = withdrawal(amount, balance);
                        }
                    }
                    case 4 -> {
                        System.out.println("Exiting...");
                        System.out.println("Thanks for using our Banking App");
                    }
                    default -> System.out.println("Invalid option");
                }
            } while (option != 4);
        }
    }
}
