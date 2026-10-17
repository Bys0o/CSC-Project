package proj;

public class EqualExpense extends Expense {

    // Everyone who shares this expense equally
    private User[] participants;

    public EqualExpense(String description, double totalAmount, User payer, User[] participants) {
        super(description, totalAmount, payer);
        if (participants == null || participants.length == 0) {
            throw new IllegalArgumentException("An equal expense needs at least one participant.");
        }
        this.participants = participants;
    }

    @Override
    public double calculateUserShare(User u) {
        for (int i = 0; i < participants.length; i++) {
            if (participants[i].equals(u)) {
                return getTotalAmount() / participants.length;
            }
        }
        return 0.0; // u is not part of this expense
    }
}