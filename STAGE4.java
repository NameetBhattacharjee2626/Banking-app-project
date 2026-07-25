import java.util.ArrayList;

// This class manages a collection of bank accounts.
class Bank {
    // The list stores all accounts created in the bank.
    private final ArrayList<BankAccount> accounts;

    // Create an empty bank when the object is first constructed.
    public Bank() {
        accounts = new ArrayList<>();
    }

    // Add a new account to the bank's collection.
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    // Search for an account by its account number.
    public BankAccount findAccountByNumber(int accountNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    // Check whether an account exists in the bank.
    public boolean accountExists(int accountNumber) {
        return findAccountByNumber(accountNumber) != null;
    }

    // Remove an account from the bank if it exists.
    public void deleteAccount(int accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        if (account != null) {
            accounts.remove(account);
            System.out.println("Deleted account: " + account);
        } else {
            System.out.println( "Account to be deleted doesn't exist.");
        }
    
    }

    // Return all accounts stored in the bank.
    public ArrayList<BankAccount> listAllAccounts() {
        return accounts;
    }

    // Return all accounts owned by the given person.
    public ArrayList<BankAccount> searchByOwner(String ownerName) {
        ArrayList<BankAccount> matches = new ArrayList<>();
        for (BankAccount acc : accounts) {
            if (acc.getOwner().equals(ownerName)) {
                matches.add(acc);
            }
        }
        return matches;
    }

    // Add up the balances of all accounts in the bank.
    public double totalBankHoldings() {
        double total = 0;
        for (BankAccount acc : accounts) {
            total += acc.getBalanceValue();
        }
        return total;
    }

    // Return every account whose balance is below the given threshold.
    public ArrayList<BankAccount> findBelowThreshold(double threshold) {
        ArrayList<BankAccount> matches = new ArrayList<>();
        for (BankAccount acc : accounts) {
            if (acc.getBalanceValue() < threshold) {
                matches.add(acc);
            }
        }
        return matches;
    }

    // Transfer money from one account to another using the correct account numbers.
    public void transferBetweenAccounts(int fromAccountNumber, int toAccountNumber, double amount) {
        BankAccount from = findAccountByNumber(fromAccountNumber);
        BankAccount to = findAccountByNumber(toAccountNumber);
        if (from != null && to != null) {
            from.transferTo(to, amount);
        } else {
            System.out.println("One or both accounts not found");
        }
    }

    public int countAccounts() {
        return accounts.size();
    }
}

// This class is the main entry point for the stage.
public class STAGE4 {
    public static void main(String[] args) {
    Bank bank = new Bank();

    for (int i = 1; i <= 10; i++) {
        BankAccount account = new BankAccount("Customer " + i, 3000 + i, 100 + (i * 25));
        bank.addAccount(account);
        System.out.println(account);
    }

        System.out.println("Account count correct: " + (bank.countAccounts() == 10));
        System.out.println("All accounts in the bank: " + bank.listAllAccounts().size());

        System.out.println("Account count correct: " + (bank.countAccounts() == 10));

BankAccount first = bank.findAccountByNumber(3001);
BankAccount second = bank.findAccountByNumber(3002);

if (first != null && second != null) {

    first.deposit(50);
    second.withdraw(20);
    bank.transferBetweenAccounts(3001, 3002, 30);


System.out.println("Withdraw/Transfer test: " + (second.getBalanceValue() == 160.0));
}

bank.deleteAccount(3006);

System.out.println("Deletion successful: " + (!bank.accountExists(3006)));
System.out.println("Account count after deletion: " + (bank.countAccounts() == 9));


System.out.println("Total holdings: " + bank.totalBankHoldings());
System.out.println("Accounts below 200: " +
        bank.findBelowThreshold(200).size());

System.out.println("Accounts owned by Customer 3: " +
        bank.searchByOwner("Customer 3").size());
}
}
