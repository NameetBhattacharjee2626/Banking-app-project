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
            System.out.println(account + " is deleted.");
        } else {
            System.out.println(accountNumber + " doesn't exist.");
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
    public int countAccounts(){
        return accounts.size();
    }
}

// This class is the main entry point for the stage.
public class STAGE4 {
    public static void main(String[] args) {
        Bank bank = new Bank();
        BankAccount first = new BankAccount("Namit", 3012, 100);
        BankAccount second = new BankAccount("Rahul", 3322, 50);

        bank.addAccount(first);
        bank.addAccount(second);

        bank.transferBetweenAccounts(3012, 3322, 25);
        System.out.println("Total holdings: " + bank.totalBankHoldings());
    }
}
