package proj;

public class ItemizedExpense extends CustomExpense {

    // Description of the items
    private String[] itemDetails;

    public ItemizedExpense(String description, double totalAmount, User payer,
                           User[] users, double[] shares, String[] itemDetails) {
        super(description, totalAmount, payer, users, shares);
        this.itemDetails = itemDetails;
    }

    public String[] getItemDetails() {
        return itemDetails;
    }

    @Override
    public double calculateUserShare(User u) {
        // Each user's share is the sum of the items assigned to them (stored in shares)
        return super.calculateUserShare(u);
    }
}