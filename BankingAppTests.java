public class BankingAppTests {
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void checkDouble(double actual, double expected, String message) {
        if (Math.abs(actual - expected) > 0.0001) {
            throw new AssertionError(message + " Expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) {
        testStage1Methods();
        testBankAccountMethods();
        testBankMethods();
        testDeleteAccount();
        System.out.println("All banking app tests passed.");
    }

    private static void testStage1Methods() {
        checkDouble(STAGE1.showBalance(125.5), 125.5, "showBalance should return the current balance");
        checkDouble(STAGE1.deposit(50, 100), 150, "deposit should increase balance for a valid amount");
        checkDouble(STAGE1.deposit(-10, 100), 100, "deposit should reject a negative amount");
        checkDouble(STAGE1.withdrawal(30, 100), 70, "withdrawal should decrease balance for a valid amount");
        checkDouble(STAGE1.withdrawal(200, 100), 100, "withdrawal should not allow overspending");
    }

    private static void testBankAccountMethods() {
        BankAccount account = new BankAccount("Namit", 1001, 100);
        check(account.getOwner().equals("Namit"), "Owner should be stored correctly");
        check(account.getAccountNumber() == 1001, "Account number should be stored correctly");
        checkDouble(account.getBalanceValue(), 100, "Initial balance should be stored correctly");

        account.deposit(50);
        checkDouble(account.getBalanceValue(), 150, "deposit should update balance");

        account.deposit(-10);
        checkDouble(account.getBalanceValue(), 150, "deposit should reject negative amounts");

        account.withdraw(40);
        checkDouble(account.getBalanceValue(), 110, "withdraw should reduce balance");

        account.withdraw(200);
        checkDouble(account.getBalanceValue(), 110, "withdraw should reject overspending");

        BankAccount recipient = new BankAccount("Rahul", 1002, 50);
        account.transferTo(recipient, 20);
        checkDouble(account.getBalanceValue(), 90, "transfer should debit the sender");
        checkDouble(recipient.getBalanceValue(), 70, "transfer should credit the recipient");

        account.transferTo(recipient, 500);
        checkDouble(account.getBalanceValue(), 90, "transfer should reject overspending");
        checkDouble(recipient.getBalanceValue(), 70, "recipient balance should not change after invalid transfer");

        String accountDescription = account.toString();
        check(accountDescription.contains("Owner: Namit"), "toString should include the owner name");
        check(accountDescription.contains("Account Number: 1001"), "toString should include the account number");
        check(accountDescription.contains("Balance:"), "toString should include the balance");
    }

    private static void testBankMethods() {
        Bank bank = new Bank();
        BankAccount first = new BankAccount("Asha", 2001, 100);
        BankAccount second = new BankAccount("Bharat", 2002, 50);
        BankAccount third = new BankAccount("Asha", 2003, 10);

        bank.addAccount(first);
        bank.addAccount(second);
        bank.addAccount(third);

        check(bank.countAccounts() == 3, "countAccounts should report the number of stored accounts");
        check(bank.accountExists(2002), "accountExists should find a stored account");
        check(!bank.accountExists(9999), "accountExists should return false for unknown account");

        check(bank.findAccountByNumber(2003) == third, "findAccountByNumber should return the matching account");
        check(bank.findAccountByNumber(9999) == null, "findAccountByNumber should return null for unknown account");

        check(bank.searchByOwner("Asha").size() == 2, "searchByOwner should return matching accounts");
        check(bank.listAllAccounts().size() == 3, "listAllAccounts should return all accounts");

        checkDouble(bank.totalBankHoldings(), 160, "totalBankHoldings should sum all balances");

        java.util.ArrayList<BankAccount> belowThreshold = bank.findBelowThreshold(60);
        check(belowThreshold.size() == 2, "findBelowThreshold should return accounts below the threshold");
        check(belowThreshold.contains(third), "findBelowThreshold should include the account below the threshold");

        bank.transferBetweenAccounts(2001, 2002, 30);
        checkDouble(first.getBalanceValue(), 70, "transferBetweenAccounts should debit the sender");
        checkDouble(second.getBalanceValue(), 80, "transferBetweenAccounts should credit the recipient");

        bank.deleteAccount(2003);
        check(!bank.accountExists(2003), "deleteAccount should remove the account from the bank");
        check(bank.countAccounts() == 2, "countAccounts should update after deletion");
    }

    private static void testDeleteAccount() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("Mina", 4001, 75);
        bank.addAccount(account);

        check(bank.countAccounts() == 1, "bank should contain the newly added account");
        check(bank.accountExists(4001), "account should exist before deletion");

        bank.deleteAccount(4001);
        check(!bank.accountExists(4001), "deleteAccount should remove the account");
        check(bank.countAccounts() == 0, "countAccounts should become zero after deleting the only account");

        bank.deleteAccount(9999);
        check(bank.countAccounts() == 0, "deleting a missing account should not change the count");
    }
}
