import java.util.Scanner;

public class STAGE1 {
    //Methods
    static double showBalance(double balance){
        return balance;
    }
    static double deposit(double amount,double balance){
        
  //// balance += deposit, not =+ (typo trap: = + is NOT +=)
        
        balance += amount;
        return balance;
    }
    static double withdrawal(double amount,double balance){
        balance -= amount;
        return balance;
    }
    //Main line

    public static void main(String[] args) {
        double balance = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            int option; //creating option variable to avoid looping problems
            do {
                System.out.print("""
                    ### Select a menu ####
                    1) Show balance:
                    2) deposit:
                    3) Withdraw: 
                    4) Exit:
                    """);
                option = scanner.nextInt();//taking input for option variable
                switch (option) {
                    case 1 -> System.out.printf("Current balance is: %.2f$\n",showBalance(balance)); //Outputs Balance
                    case 2 -> {
                        System.out.print("Enter deposit amount: ");
                        double amount = scanner.nextDouble();
                        if (amount<=0) { // to check the validity of the entered amount
                            System.out.print("Please Input a Valid Amount");
                        }
                        else{
                            balance = deposit(amount, balance);//Adds amount in the Current balance
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();
                        if (amount<=0) { //checks the validity of withdrawal amount
                            System.out.print("Please Input a Valid Amount");
                        }
                        else if (amount>balance){ //ensures if the amount is less than or equal to current balance
                            System.out.print("Not enough balance");
                        }
                        else{
                        balance = withdrawal(amount, balance);//Subtract amount from the balance
                    }
                    }
                    case 4 ->{ System.out.println("Exiting...");
                        System.out.println("Thanks for using our Banking App");
                    }//Exits from the Program
                    default -> System.out.println("Invalid option");//If someone enters a Invalid number apart from the Given Options
                }
            } while (option != 4);
        }
    }
}
