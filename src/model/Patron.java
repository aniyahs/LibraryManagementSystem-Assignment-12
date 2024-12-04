package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library patron (user) in the system.
 * A patron can borrow books, return them, and is subject to membership constraints.
 */
public class Patron {
    private String name;
    private String patronId; // Unique identifier for the patron.
    private PatronType membershipType; // Enum for membership type (e.g., REGULAR, PREMIUM).
    private List<Book> borrowedBooks; // Books currently borrowed by the patron.
    private double outstandingFees; // Outstanding late fees or charges.

    /**
     * Constructor to create a new Patron object.
     *
     * @param name           The name of the patron (non-null, non-empty).
     * @param patronId       A unique identifier for the patron (non-null, non-empty).
     * @param membershipType The type of membership the patron has (non-null).
     */
    public Patron(String name, String patronId, PatronType membershipType) {
        validateString(name, "Name");
        validateString(patronId, "Patron ID");
        validateNonNull(membershipType, "Membership Type");

        this.name = name;
        this.patronId = patronId;
        this.membershipType = membershipType;
        this.borrowedBooks = new ArrayList<>();
        this.outstandingFees = 0.0;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateString(name, "Name");
        this.name = name;
    }

    public String getPatronId() {
        return patronId;
    }

    public void setPatronId(String patronId) {
        validateString(patronId, "Patron ID");
        this.patronId = patronId;
    }

    public PatronType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(PatronType membershipType) {
        validateNonNull(membershipType, "Membership Type");
        this.membershipType = membershipType;
    }

    public List<Book> getBorrowedBooks() {
        // Returning an unmodifiable list to preserve encapsulation.
        return List.copyOf(borrowedBooks);
    }

    public double getOutstandingFees() {
        return outstandingFees;
    }

    /**
     * Adds a book to the list of borrowed books if the patron is within their borrowing limit.
     *
     * @param book The book to borrow (non-null).
     * @throws IllegalStateException If the patron has exceeded their borrowing limit.
     */
    public void borrowBook(Book book) {
        validateNonNull(book, "Book");

        if (borrowedBooks.size() >= membershipType.getBorrowingLimit()) {
            throw new IllegalStateException("Borrowing limit exceeded for this membership type.");
        }

        if (!book.borrowBook()) {
            throw new IllegalStateException("The book is not available for borrowing.");
        }

        borrowedBooks.add(book);
    }

    /**
     * Returns a borrowed book and removes it from the patron's list of borrowed books.
     *
     * @param book The book to return (non-null).
     * @throws IllegalStateException If the book is not in the patron's borrowed list.
     */
    public void returnBook(Book book) {
        validateNonNull(book, "Book");

        if (!borrowedBooks.contains(book)) {
            throw new IllegalStateException("This book was not borrowed by the patron.");
        }

        if (!book.returnBook()) {
            throw new IllegalStateException("The book could not be returned due to an internal issue.");
        }

        borrowedBooks.remove(book);
    }

    /**
     * Adds late fees to the patron's outstanding balance.
     *
     * @param feeAmount The amount of fees to add (>= 0).
     */
    public void addLateFee(double feeAmount) {
        validateNonNegative(feeAmount, "Fee Amount");
        outstandingFees += feeAmount;
    }

    /**
     * Clears the patron's outstanding fees.
     */
    public void clearOutstandingFees() {
        outstandingFees = 0.0;
    }

    // Private utility methods for validation
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    private void validateNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }

    private void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return patronId.equals(patron.patronId); // Unique identifier comparison.
    }

    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "name='" + name + '\'' +
                ", patronId='" + patronId + '\'' +
                ", membershipType=" + membershipType +
                ", borrowedBooks=" + borrowedBooks.size() + " books" +
                ", outstandingFees=" + outstandingFees +
                '}';
    }
}
