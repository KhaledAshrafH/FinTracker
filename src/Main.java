import java.util.*;

public class Main {

    // Class to represent a financial transaction
    static class Transaction {
        String category;
        String description;
        double amount;

        Transaction(String category,String description, double amount) {
            this.category = category;
            this.description = description;
            this.amount = amount;
        }
    }


    static List<Transaction> transactions = new ArrayList<>();

    // Track income and expense totals for quick summary calculations (O(1) time)
    static double totalIncome = 0;
    static double totalExpenses = 0;

    static int choice;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            printMenu();
            getUserChoice();
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewAllTransactions();
                    break;
                case 3:
                    viewSummary();
                    break;
                case 4:
                    getInsights();
                    break;
                case 5:
                    System.out.println("Exit the program. GoodBye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again!");
            }
        }
    }

    // Take from user the transaction details and adds it to the list
    static void addTransaction() {
        System.out.print("Enter Transaction Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Transaction Amount (positive for income, negative for expense): ");
        double amount = scanner.nextDouble();

        // Update totals directly for quick summary access
        if(amount < 0) totalExpenses+=amount;
        else totalIncome+=amount;

        System.out.print("Enter Transaction Category: ");
        String category = scanner.next();
        scanner.nextLine();

        Transaction transaction = new Transaction(category,description,amount);
        transactions.add(transaction);
        System.out.println("Transaction added successfully!");
    }

    // Displays all transactions with an option to sort by amount
    static void viewAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No Transactions available now!");
            return;
        }
        System.out.println("\nAll Transactions:");
        viewTransactionsUtility();
        System.out.print("Do you want to sort transactions by amount? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next().toLowerCase();
        if (answer.equalsIgnoreCase("y")) {
            transactions.sort(Comparator.comparingDouble(t -> t.amount));
            System.out.println("Transactions sorted by amount (low to high).");
            System.out.println("\nAll Transactions (Sorted):");
            viewTransactionsUtility();
        }
    }

    // Helper method to display transaction details in a formatted table
    static private void viewTransactionsUtility(){
        System.out.printf("%-20s %-10s %-15s%n", "Description", "Amount", "Category");
        System.out.println("-----------------------------------------");

        for (Transaction transaction : transactions)
            System.out.printf("%-20s %-10.2f %-15s%n",
                    transaction.description, transaction.amount, transaction.category);
    }

    // Displays Financial Summary
    static void viewSummary() {
        System.out.println("\nFinancial Summary:");
        System.out.println("Total Income: "+ totalIncome);
        System.out.println("Total Expenses: "+ totalExpenses);
        System.out.println("Balance: "+ (totalIncome + totalExpenses));
    }

    // Analyzes transaction categories and spending habits
    static void getInsights() {
        Map<String,Double> categories=new HashMap<>();
        for (Transaction transaction : transactions) {
            if(transaction.amount<0){
                if(categories.containsKey(transaction.category))
                    categories.put(transaction.category,
                            categories.get(transaction.category)+(transaction.amount*-1));
                else
                    categories.put(transaction.category,transaction.amount*-1);
            }
        }

        System.out.println("\nSpending Insights:");
        System.out.println("Total Expenses: "+ totalExpenses*-1);
        for (Map.Entry<String, Double> category : categories.entrySet())
            System.out.println("Category: " + category.getKey() + " - Spent: " + category.getValue()
                    + " ("+ String.format("%.1f%%", (category.getValue()/(totalExpenses*-1))*100 )+")" );
    }

    // Gets the user's choice from the menu
    private static void getUserChoice() {
        try {
            choice = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            choice = 0;
        }
    }

    // Displays the app menu
    static public void printMenu(){
        System.out.println("\nHello, Welcome to FinTracker App");
        System.out.println("----------------------------------");
        System.out.println("1. Input Transactions");
        System.out.println("2. View Transactions");
        System.out.println("3. View Summary");
        System.out.println("4. Get Insights");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
}