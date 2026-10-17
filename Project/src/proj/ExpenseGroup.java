package proj;

public class ExpenseGroup implements Settlable {
	
	private String groupName;
	private User[] users;
	private int UserCount;
	private Expense[] expenses;
	private int expenseCount;
	
	public ExpenseGroup(String groupName, int maxUsers, int maxExpenses) {
		
		this.groupName = groupName;
		this.users = new User[maxUsers];
		this.expenses = new Expense[maxExpenses];
		
		UserCount = 0;
		expenseCount = 0;
		
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean addUser(User u) {
		
		if (UserCount < users.length) {
			users[UserCount] = u;
			UserCount++;
			return true;
		}
		
		return false;
	}
	
	public boolean addExpense(Expense e) {
		
		if (expenseCount < expenses.length) {
			expenses[expenseCount] = e;
			expenseCount++;
			return true;
		}
		
		return false; 
	}
	
	public boolean removeExpense(String desc) {
		
		if (expenseCount == 0 )
			return false;
		
		for (int i=0; i < expenseCount ; i++) {
			if (expenses[i].getDescription().equalsIgnoreCase(desc)) {
				
				for (int j = i ; j < expenseCount - 1 ; j++) {
					expenses[j] = expenses[j + 1];
				}
				
				expenses[expenseCount - 1] = null;
				expenseCount--;
				return true;
			}
		}
		return false;
	}
	
	public Expense searchExpenseByTitle(String title) {
		
		for(int i = 0 ; i < expenseCount ; i++) {
			if (expenses[i].getDescription().equalsIgnoreCase(title))
				return expenses[i];
		}
		
		return null;
	}
	
	public double calculateTotalExpensesRecursive(int index) {
		
		if (index >= expenseCount || expenses[index] == null)
			return 0.0;
		
		return expenses[index].getTotalAmount() + calculateTotalExpensesRecursive(index + 1);
	}
	
	public void settleBalance() {
		
		System.out.println("Settling balance for all members in group : " + groupName);
		for (int i = 0 ; i < UserCount ; i++) {
			if (users[i] != null)
				users[i].settleBalance();
		}
	}
}