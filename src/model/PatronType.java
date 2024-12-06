package model;

/**
 * Enum representing different patron membership types.
 */
public enum PatronType {
    REGULAR(5), // Regular patrons can borrow up to 5 books.
    PREMIUM(10); // Premium patrons can borrow up to 10 books.

    private final int borrowingLimit;

    PatronType(int borrowingLimit) {
        this.borrowingLimit = borrowingLimit;
    }

    public int getBorrowingLimit() {
        return borrowingLimit;
    }
}
