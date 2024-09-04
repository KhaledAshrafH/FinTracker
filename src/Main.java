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

    // Transaction management methods

    // Take from user the transaction details and adds it to the list
    static void addTransaction() {
        String description = getDescriptionInput();
        double amount = getAmountInput();
        String category = getCategoryInput();
        updateTotals(amount);  // Update totals directly for quick summary access

        Transaction transaction = createTransaction(category, description, amount);
        transactions.add(transaction);
        System.out.println("Transaction added successfully!");
    }

    static String getDescriptionInput() {
        System.out.print("Enter Transaction Description: ");
        return scanner.nextLine();
    }

    static double getAmountInput() {
        System.out.print("Enter Transaction Amount (positive for income, negative for expense): ");
        return scanner.nextDouble();
    }

    static String getCategoryInput() {
        System.out.print("Enter Transaction Category: ");
        String category = scanner.next();
        scanner.nextLine(); // Consume newline character
        return category;
    }

    static void updateTotals(double amount) {
        if(amount < 0) {
            totalExpenses += amount;
        } else {
            totalIncome += amount;
        }
    }

    static Transaction createTransaction(String category, String description, double amount) {
        return new Transaction(category, description, amount);
    }

    // Transaction display methods

    // Displays all transactions with an option to sort by amount
    static void viewAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No Transactions available now!");
            return;
        }

        System.out.println("\nAll Transactions:");
        viewTransactionsUtility();

        if (promptForSorting()) {
            transactions.sort(Comparator.comparingDouble(t -> t.amount)); // Method reference for cleaner code
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

    private static boolean promptForSorting() {
        System.out.print("Do you want to sort transactions by amount? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next().toLowerCase();
        return answer.equalsIgnoreCase("y");
    }

    // Summary and insight methods

    // Displays Financial Summary
    static void viewSummary() {
        System.out.println("\nFinancial Summary:");
        System.out.println("Total Income: "+ totalIncome);
        System.out.println("Total Expenses: "+ totalExpenses);
        System.out.println("Balance: "+ (totalIncome + totalExpenses));
    }

    // Analyzes transaction categories and spending habits
    static void getInsights() {
        Map<String, Double> categories = calculateCategories();
        printSpendingInsights(categories);
    }

    static Map<String, Double> calculateCategories() {
        Map<String, Double> categories = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.amount < 0) {
                double amount = transaction.amount * -1;
                categories.put(transaction.category, categories.getOrDefault(transaction.category, 0.0) + amount);
            }
        }
        return categories;
    }

    static void printSpendingInsights(Map<String, Double> categories) {
        double totalExpenses = calculateTotalExpenses(categories);
        System.out.println("\nSpending Insights:");
        System.out.println("Total Expenses: " + totalExpenses * -1);

        for (Map.Entry<String, Double> category : categories.entrySet()) {
            double spentPercentage = (category.getValue() / (totalExpenses * -1)) * 100;
            System.out.println("Category: " + category.getKey() + " - Spent: " + category.getValue()
                    + " (" + String.format("%.1f%%", spentPercentage) + ")");
        }
    }

    static double calculateTotalExpenses(Map<String, Double> categories) {
        double totalExpenses = 0;
        for (double amount : categories.values()) {
            totalExpenses += amount;
        }
        return totalExpenses*-1;
    }

    // Menu and user choice handling methods

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