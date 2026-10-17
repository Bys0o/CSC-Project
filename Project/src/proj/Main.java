package proj;

import java.util.Scanner;

public class Main {

	private static final int MAX_USERS = 20;
	private static final int MAX_EXPENSES = 50;

	private static Scanner input = new Scanner(System.in);
	private static User[] users = new User[MAX_USERS];
	private static int userCount = 0;
	private static ExpenseGroup group;

	// Prevents removing expenses after balances are zeroed out
	private static boolean balancesSettled = false;

	public static void main(String[] args) {

		System.out.println("=== QOTTA | GROUP EXPENSE SPLITTER ===");
		String groupName = readText("Enter group name: ");
		group = new ExpenseGroup(groupName, MAX_USERS, MAX_EXPENSES);

		int choice;
		do {
			printMenu();
			choice = readInt("Your choice: ");

			switch (choice) {
			case 1:
				addUser();
				break;
			case 2:
				addExpense();
				break;
			case 3:
				findExpense();
				break;
			case 4:
				removeExpense();
				break;
			case 5:
				showTotal();
				break;
			case 6:
				showBalances();
				break;
			case 7:
				settleUp();
				break;
			case 8:
				System.out.println("Thank you for using Qotta. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please choose a number between 1 and 8.");
			}
		} while (choice != 8);
	}

	// -----
	// Menu
	// -----

	private static void printMenu() {
		System.out.println();
		System.out.println("========== MAIN MENU ==========");
		System.out.println("1. Add new user");
		System.out.println("2. Add new expense");
		System.out.println("3. Search expense details");
		System.out.println("4. Remove an expense");
		System.out.println("5. Show total group spending");
		System.out.println("6. Show balances");
		System.out.println("7. Settle balances");
		System.out.println("8. Exit");
	}

	// ----------------
	// Menu operations
	// ----------------

	private static void addUser() {
		System.out.println();
		System.out.println("--- Add new user ---");
		String id = readText("Enter user ID: ");
		if (findUser(id) != null) {
			System.out.println("A user with ID " + id + " already exists.");
			return;
		}
		String name = readText("Enter user name: ");

		User u = new User(id, name, 0);
		if (group.addUser(u)) {
			users[userCount] = u;
			userCount++;
			System.out.println("User " + name + " was added.");
		} else {
			System.out.println("The group is full.");
		}
	}

	private static void addExpense() {
		System.out.println();
		System.out.println("--- Add new expense ---");
		if (userCount == 0) {
			System.out.println("Please add at least one user first.");
			return;
		}

		System.out.println("Split type:");
		System.out.println("1. Equal (everyone pays the same)");
		System.out.println("2. Custom (a specific amount for each user)");
		System.out.println("3. Itemized (by the items each user ordered)");
		int type = readInt("Your choice: ");
		if (type < 1 || type > 3) {
			System.out.println("Invalid split type.");
			return;
		}

		String description = readText("Description: ");
		if (group.searchExpenseByTitle(description) != null) {
			System.out.println("An expense called " + description + " already exists.");
			return;
		}
		double total = readDouble("Total amount: ");
		User payer = readUser("Paid by (user ID): ");
		if (payer == null) {
			return;
		}

		int n = readInt("Number of users sharing this expense: ");
		if (n <= 0 || n > userCount) {
			System.out.println("The number must be between 1 and " + userCount + ".");
			return;
		}

		User[] participants = new User[n];
		for (int i = 0; i < n; i++) {
			User p = readUser("User " + (i + 1) + " ID: ");
			if (p == null) {
				return;
			}
			boolean duplicate = false;
			for (int j = 0; j < i; j++) {
				if (participants[j].equals(p)) {
					duplicate = true;
					break;
				}
			}
			if (duplicate) {
				System.out.println("This user is already added to this expense. Try again.");
				i--;
				continue;
			}
			participants[i] = p;
		}

		Expense expense;
		try {
			if (type == 1) {
				expense = new EqualExpense(description, total, payer, participants);
			} else {
				double[] shares = new double[n];
				for (int i = 0; i < n; i++) {
					shares[i] = readDouble("Amount for " + participants[i].getName() + ": ");
				}

				if (type == 2) {
					expense = new CustomExpense(description, total, payer, participants, shares);
				} else {
					int itemCount = readInt("Number of items: ");
					// Fixed: Strict validation to ensure at least 1 item
					while (itemCount <= 0) {
						System.out.println("An itemized expense must have at least one item.");
						itemCount = readInt("Number of items: ");
					}
					String[] items = new String[itemCount];
					for (int i = 0; i < itemCount; i++) {
						items[i] = readText("Item " + (i + 1) + " (name - price): ");
					}
					expense = new ItemizedExpense(description, total, payer, participants, shares, items);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Could not add the expense: " + e.getMessage());
			return;
		}

		if (group.addExpense(expense)) {
			applyExpense(expense, true);
			balancesSettled = false; // Reset the lock when a new expense alters balances
			System.out.println("Expense " + description + " was added.");
		} else {
			System.out.println("The group cannot hold more expenses.");
		}
	}

	private static void findExpense() {
		System.out.println();
		System.out.println("--- Search expense details ---");
		String title = readText("Enter the description: ");
		Expense e = group.searchExpenseByTitle(title);

		if (e == null) {
			System.out.println("Expense " + title + " was not found.");
			return;
		}

		System.out.println("Description  : " + e.getDescription());
		System.out.println("Total amount : " + round(e.getTotalAmount()));
		System.out.println("Paid by      : " + e.getPayer().getName());

		if (e instanceof ItemizedExpense) {
			System.out.println("Items included:");
			String[] items = ((ItemizedExpense) e).getItemDetails();
			if (items != null) {
				for (int i = 0; i < items.length; i++) {
					System.out.println("  - " + items[i]);
				}
			}
		}

		System.out.println("Each user's share:");
		for (int i = 0; i < userCount; i++) {
			double share = e.calculateUserShare(users[i]);
			// Fixed: Only print the users who actually owe something for this specific
			// expense
			if (share > 0) {
				System.out.println("  " + users[i].getName() + " pays " + round(share));
			}
		}
	}

	private static void removeExpense() {
		System.out.println();
		System.out.println("--- Remove expense ---");

		// Fixed: Prevent removal if balances have already been wiped to 0
		if (balancesSettled) {
			System.out.println("Cannot remove expenses after balances have been settled.");
			return;
		}

		String title = readText("Enter the description: ");
		Expense e = group.searchExpenseByTitle(title);
		if (e == null) {
			System.out.println("Expense " + title + " was not found.");
			return;
		}
		if (group.removeExpense(title)) {
			applyExpense(e, false);
			System.out.println("Expense " + title + " was removed.");
		} else {
			System.out.println("Expense " + title + " could not be removed.");
		}
	}

	private static void showTotal() {
		System.out.println();
		System.out.println("--- Total group spending ---");
		double total = group.calculateTotalExpensesRecursive(0);
		System.out.println("Total of all expenses = " + round(total));
	}

	private static void showBalances() {
		System.out.println();
		System.out.println("--- Balances ---");
		for (int i = 0; i < userCount; i++) {
			double balance = users[i].getBalance();
			if (balance < 0) {
				System.out.println(users[i].getName() + " owes " + round(-balance));
			} else if (balance > 0) {
				System.out.println(users[i].getName() + " is owed " + round(balance));
			} else {
				System.out.println(users[i].getName() + " is settled");
			}
		}
	}

	private static void settleUp() {
		System.out.println();
		System.out.println("--- Settle balances ---");
		group.settleBalance();

		System.out.println();
		for (int i = 0; i < userCount; i++) {
			double balance = users[i].getBalance();
			if (balance < 0) {
				System.out.println(users[i].getName() + " pays " + round(-balance));
			} else if (balance > 0) {
				System.out.println(users[i].getName() + " receives " + round(balance));
			} else {
				System.out.println(users[i].getName() + " is already settled");
			}
			users[i].setBalance(0);
		}
		System.out.println("All balances are now settled.");

		// Fixed: Lock expense removal until a new one is added
		balancesSettled = true;
	}

	// ---------------
	// Helper methods
	// ---------------

	private static void applyExpense(Expense e, boolean isAdd) {
		for (int i = 0; i < userCount; i++) {
			double share = e.calculateUserShare(users[i]);
			if (isAdd) {
				users[i].setBalance(users[i].getBalance() - share);
			} else {
				users[i].setBalance(users[i].getBalance() + share);
			}
		}
		User payer = e.getPayer();
		if (isAdd) {
			payer.setBalance(payer.getBalance() + e.getTotalAmount());
		} else {
			payer.setBalance(payer.getBalance() - e.getTotalAmount());
		}
	}

	private static double round(double value) {
		return Math.round(value * 100) / 100.0;
	}

	private static User findUser(String id) {
		for (int i = 0; i < userCount; i++) {
			if (users[i].getUserId().equalsIgnoreCase(id)) {
				return users[i];
			}
		}
		return null;
	}

	private static User readUser(String message) {
		String id = readText(message);
		User u = findUser(id);
		if (u == null) {
			System.out.println("No user with ID " + id + ".");
		}
		return u;
	}

	private static String readText(String message) {
		System.out.print(message);
		return input.nextLine().trim();
	}

	private static int readInt(String message) {
		while (true) {
			try {
				return Integer.parseInt(readText(message));
			} catch (NumberFormatException e) {
				System.out.println("Please enter a whole number.");
			}
		}
	}

	private static double readDouble(String message) {
		while (true) {
			try {
				return Double.parseDouble(readText(message));
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
			}
		}
	}
}
