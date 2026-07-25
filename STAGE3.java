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
