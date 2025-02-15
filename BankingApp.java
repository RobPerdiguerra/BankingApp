import java.util.*;

public class BankingApp {
    private static class Account {
        String userId;
        String pin;
        String userName;
        double balance;
        List<String> transactionHistory;

        Account(String userId, String pin, String userName, double balance) {
            this.userId = userId;
            this.pin = pin;
            this.userName = userName;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
        }
    }

    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentUser;

    public static void main(String[] args) {
        // Initialize accounts with unique user IDs and PINs
        accounts.put("412435", new Account("412435", "7452", "Chris Sandoval", 32000.0 ));
        accounts.put("264863", new Account("264863", "1349", "Marc Yim", 1000.0));
        accounts.put("1", new Account("1", "5455", "JCash", 1.0));

        if (login()) {
            dashboard();
        } else {
            System.out.println("Invalid credentials. Exiting...");
        }
    }

    private static boolean login() {
        System.out.println("Welcome to JCash!");
        System.out.print("Enter ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (accounts.containsKey(userId) && accounts.get(userId).pin.equals(pin)) {
            currentUser = accounts.get(userId);
            return true;
        }
        return false;
    }

    private static void dashboard() {
        while (true) {
            System.out.println("\nYou are Logged in!");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Your balance: ₱" + currentUser.balance);
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    viewTransactionHistory();
                    return;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            currentUser.balance += amount;
            currentUser.transactionHistory.add("Deposited ₱" + amount);
            System.out.println("Deposited $" + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        currentUser.transactionHistory.add("Withdrew ₱" + amount);
        if (amount > 0 && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            System.out.println("Withdrawn $" + amount);
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    private static void transfer() {
        System.out.print("Enter recipient user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        if (accounts.containsKey(recipientId) && amount > 0 && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            accounts.get(recipientId).balance += amount;
            currentUser.transactionHistory.add("Transferred $" + amount + " to " + recipientId);
            accounts.get(recipientId).transactionHistory.add("Received $" + amount + " from " + currentUser.userId);
            System.out.println("Transferred $" + amount + " to " + recipientId);
        } else {
            System.out.println("Invalid transaction.");
        }
    }
    private static void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (currentUser.transactionHistory.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (String transaction : currentUser.transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}
