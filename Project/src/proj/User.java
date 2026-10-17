package proj;

public class User implements Settlable {
	
	private String userId;
	private String name;
	private double balance;
	
	public User(String userId,String name,double balance) {
		
		this.userId = userId;
		this.name = name;
		this.balance = balance;
		
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void settleBalance() {
		
		System.out.println("Settling balance for user : " + name + " with current balance : " + balance);
		
	}
	
	public String toString() {
		
		return "User ID : " + userId + "\nname : " + name + "\nBalance : " + balance;
		
	}
	
}