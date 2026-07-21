class BankAccount {
    // These are the private data stored inside each account.
    // Private means other classes cannot change them directly.
    private double balance;
    private String owner;
    private int accountNumber;

    // This method adds money to the account.
    // It refuses negative or zero values.
   public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        balance += amount;
    }

    // This method takes money out of the account.
    // It stops if the amount is negative or if the account has too little money.
   public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Invalid amount");
            return;
        } else if (amount > balance) {
            System.out.println("Not enough balance");
            return;
        }

        balance -= amount;
    }

    // This method sends money from this account to another account.
    public void transferTo(BankAccount other, double amount) {
        if (amount < 0) {
            System.out.println("Invalid amount");
            return;
        } else if (amount > balance) {
            System.out.println("Not enough balance");
            return;
        }

        // Add the money to the receiving account.
        other.balance += amount;
        // Remove the money from the sending account.
        balance -= amount;
    }

    // This method returns the current balance so we can test the account behavior.
    public double getBalanceValue() {
        return balance;
    }

    // These methods are placeholders for now.
    // They are meant to return information about the account.
    public String getOwner() {
        return owner;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public BankAccount(String owner, int accountNumber, double startingBalance) {
    this.owner = owner;
    this.accountNumber = accountNumber;
    this.balance = startingBalance;
}

}

public class STAGE3 {
    public static void main(String[] args) {
        // Create two account objects to test the banking actions.
       BankAccount account1 = new BankAccount("Namit", 3012, 0);
       BankAccount account2 = new BankAccount("Rahul", 3322, 0);
        // Test 1: Deposit money into the first account.
        account1.deposit(100);

        // Test 2: Withdraw money from the first account.
        account1.withdraw(30);

        // Test 3: Transfer money from account1 to account2.
        account1.transferTo(account2, 20);

        // Test 4: Try invalid actions to make sure they are rejected.
        account1.deposit(-10);
        account2.withdraw(1000);

        // Show the result of the tests.
        System.out.println("Account 1 balance: " + account1.getBalanceValue());
        System.out.println("Account 2 balance: " + account2.getBalanceValue());

        // Check whether the results match what we expect.
        if (account1.getBalanceValue() == 50 && account2.getBalanceValue() == 20) {
            System.out.println("All tests passed.");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}
