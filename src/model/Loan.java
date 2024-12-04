package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a loan transaction between a patron and a book in the library system.
 */
public class Loan {
    private Book book; // The book being borrowed.
    private Patron patron; // The patron who borrowed the book.
    private LocalDate loanDate; // The date the book was borrowed.
    private LocalDate dueDate; // The date the book is due for return.
    private LocalDate returnDate; // The date the book was returned (nullable).

    /**
     * Constructor to create a Loan object.
     *
     * @param book     The book being borrowed (non-null).
     * @param patron   The patron borrowing the book (non-null).
     * @param loanDate The date the book is borrowed (non-null).
     * @param dueDate  The date the book is due (non-null and after loanDate).
     */
    public Loan(Book book, Patron patron, LocalDate loanDate, LocalDate dueDate) {
        validateNonNull(book, "Book");
        validateNonNull(patron, "Patron");
        validateNonNull(loanDate, "Loan Date");
        validateNonNull(dueDate, "Due Date");
        if (!dueDate.isAfter(loanDate)) {
            throw new IllegalArgumentException("Due Date must be after Loan Date.");
        }

        this.book = book;
        this.patron = patron;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null; // Return date is initially null.
    }

    // Getters and Setters

    /**
     * Gets the book associated with this loan.
     * @return The borrowed book.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Gets the patron associated with this loan.
     * @return The patron who borrowed the book.
     */
    public Patron getPatron() {
        return patron;
    }

    /**
     * Gets the loan date.
     * @return The date the book was borrowed.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Gets the due date for the loan.
     * @return The date the book is due for return.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Gets the return date of the book.
     * @return The date the book was returned (nullable).
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date for the loan.
     * This also checks whether the return date is valid (on or after the loan date).
     *
     * @param returnDate The date the book was returned.
     * @throws IllegalArgumentException If the return date is before the loan date.
     */
    public void setReturnDate(LocalDate returnDate) {
        validateNonNull(returnDate, "Return Date");
        if (returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Return Date cannot be before Loan Date.");
        }
        this.returnDate = returnDate;
    }

    /**
     * Checks if the loan is overdue based on the current date.
     * 
     * @return true if the loan is overdue, false otherwise.
     */
    public boolean isOverdue() {
        if (returnDate != null) {
            return returnDate.isAfter(dueDate);
        }
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Calculates the late fee for the loan based on the number of overdue days.
     *
     * @param dailyLateFee The fee charged per day of being overdue (non-negative).
     * @return The total late fee, or 0.0 if the loan is not overdue.
     * @throws IllegalArgumentException If the daily late fee is negative.
     */
    public double calculateLateFee(double dailyLateFee) {
        validateNonNegative(dailyLateFee, "Daily Late Fee");

        if (!isOverdue()) {
            return 0.0;
        }

        LocalDate effectiveReturnDate = (returnDate != null) ? returnDate : LocalDate.now();
        long overdueDays = effectiveReturnDate.toEpochDay() - dueDate.toEpochDay();
        return overdueDays * dailyLateFee;
    }

    // Utility methods for validation

    /**
     * Validates that a value is not null.
     *
     * @param value     The value to validate.
     * @param fieldName The name of the field being validated.
     * @throws IllegalArgumentException If the value is null.
     */
    private void validateNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }

    /**
     * Validates that a numeric value is non-negative.
     *
     * @param value     The value to validate.
     * @param fieldName The name of the field being validated.
     * @throws IllegalArgumentException If the value is negative.
     */
    private void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    // Overridden methods

    /**
     * Checks equality based on the book and patron.
     * 
     * @param o The other object to compare.
     * @return true if the loans are for the same book and patron.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(book, loan.book) &&
               Objects.equals(patron, loan.patron) &&
               Objects.equals(loanDate, loan.loanDate);
    }

    /**
     * Generates a hash code for the Loan object.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(book, patron, loanDate);
    }

    /**
     * Returns a string representation of the Loan object.
     * 
     * @return A readable string representation.
     */
    @Override
    public String toString() {
        return "Loan{" +
               "book=" + book.getTitle() +
               ", patron=" + patron.getName() +
               ", loanDate=" + loanDate +
               ", dueDate=" + dueDate +
               ", returnDate=" + returnDate +
               '}';
    }
}
