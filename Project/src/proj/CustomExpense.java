package proj;

public class CustomExpense extends Expense {

	// users[i] owes shares[i]
	private User[] users;
	private double[] shares;

	public CustomExpense(String description, double totalAmount, User payer, User[] users, double[] shares) {
		super(description, totalAmount, payer);

		if (users.length != shares.length) {
			throw new IllegalArgumentException("Each user must have exactly one share.");
		}

		double sum = 0;
		for (int i = 0; i < shares.length; i++) {
			if (shares[i] < 0) {
				throw new IllegalArgumentException("A share cannot be negative.");
			}
			sum += shares[i];
		}
		if (Math.abs(sum - totalAmount) > 0.01) {
			throw new IllegalArgumentException(
					"Shares (" + sum + ") must add up to the total amount (" + totalAmount + ").");
		}

		this.users = users;
		this.shares = shares;
	}

	public User[] getUsers() {
		return users;
	}

	public double[] getShares() {
		return shares;
	}

	@Override
	public double calculateUserShare(User u) {
		for (int i = 0; i < users.length; i++) {
			if (users[i].equals(u)) {
				return shares[i];
			}
		}
		return 0.0; // u is not part of this expense
	}
}