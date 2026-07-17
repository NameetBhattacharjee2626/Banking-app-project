import java.util.Scanner;

public class STAGE1 {
    //Methods
    static double ShowBalance(double balance){
        return balance;
    }
    static double deposit(double deposit,double balance){
        
        /// Earlier it was balance += deposit;
        
        /// Therefore when we added 30 then again added 50 the output was shown 50 not 80 because
        /// =+ is not +=. Java doesn't care about your spacing — balance=+deposit; 
        /// is parsed as balance = (+deposit); 
        /// — that's an assignment of the unary plus of deposit (which does nothing useful; unary + on a number
        ///  just returns the number itself). So every single deposit call overwrites balance with whatever you 
        /// just deposited, throwing away everything that was there before.
        
        
        
        balance += deposit;
        return balance;
    }
    static double withdrawal(double withdrwal,double balance){
        balance -= withdrwal;
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
                    2) Deposit:
                    3) Withdraw: 
                    4) Exit:
                    """);
                option = scanner.nextInt();//taking input for option variable
                switch (option) {
                    case 1 -> System.out.printf("Current balance is: %.2f\n $",ShowBalance(balance)); //Outputs Balance
                    case 2 -> {
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        balance = deposit(depositAmount, balance);//Adds Deposited ammount in the Current balance
                    }
                    case 3 -> {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        balance = withdrawal(withdrawalAmount, balance);//Substracts Withdrawed money from the balance
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
