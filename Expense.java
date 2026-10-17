package proj;

public abstract class Expense {
	
	private String description;
	private double totalAmount;
	private User payer;
	
	public Expense(String description, double totalAmount, User payer) {
		
		this.description = description;
		this.totalAmount = totalAmount;
		this.payer = payer;
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public User getPayer() {
		return payer;
	}

	public void setPayer(User payer) {
		this.payer = payer;
	}

	public abstract double calculateUserShare(User u);
	
}