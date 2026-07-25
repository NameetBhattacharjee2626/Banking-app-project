// This class represents a single bank account with its owner, account number, and balance.
class BankAccount {
    // These fields are private so other classes cannot change them directly.
    private double balance;
    private final String owner;
    private final int accountNumber;
    @Override
public String toString() {
    return "{Owner: " + owner +"}, {Account Number: " + accountNumber +"}, {Balance: " + balance + "}.";
}

    // Create a new account with an owner name, account number, and starting balance.
    public BankAccount(String owner, int accountNumber, double startingBalance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = startingBalance;
    }

    // Add money to the account when the amount is valid.
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        balance += amount;
    }

    // Remove money from the account if the amount is valid and funds are available.
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

    // Transfer money from this account to another account.
    public void transferTo(BankAccount other, double amount) {
        if (amount < 0) {
            System.out.println("Invalid amount");
            return;
        } else if (amount > balance) {
            System.out.println("Not enough balance");
            return;
        }

        // Update both accounts after a successful transfer.
        other.balance += amount;
        balance -= amount;
    }

    // Return the current balance of the account.
    public double getBalanceValue() {
        return balance;
    }

    // Return the owner name of the account.
    public String getOwner() {
        return owner;
    }

    // Return the account number.
    public int getAccountNumber() {
        return accountNumber;
    }
}
