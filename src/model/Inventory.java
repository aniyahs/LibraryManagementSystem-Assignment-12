package model;

import java.util.Objects;

/**
 * Represents the inventory details for a specific book in the library.
 * Tracks the book's ISBN, the number of copies available, and total copies.
 */
public class Inventory {

    private final String isbn;
    private int totalCopies;
    private int availableCopies;

    /**
     * Constructs an Inventory object.
     *
     * @param isbn           The ISBN of the book.
     * @param totalCopies    The total number of copies for the book.
     * @param availableCopies The number of available copies.
     */
    public Inventory(String isbn, int totalCopies, int availableCopies) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }
        if (totalCopies < 0 || availableCopies < 0) {
            throw new IllegalArgumentException("Total copies and available copies cannot be negative.");
        }
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies.");
        }

        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return The ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the total number of copies for the book.
     *
     * @return The total number of copies.
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Updates the total number of copies for the book.
     * Adjusts the available copies proportionally if needed.
     *
     * @param totalCopies The new total number of copies.
     */
    public void setTotalCopies(int totalCopies) {
        if (totalCopies < availableCopies) {
            throw new IllegalArgumentException("Total copies cannot be less than available copies.");
        }
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative.");
        }
        this.totalCopies = totalCopies;
    }

    /**
     * Returns the number of available copies for the book.
     *
     * @return The number of available copies.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Updates the availability of the book when a copy is borrowed.
     */
    public void borrowBook() {
        if (availableCopies <= 0) {
            throw new IllegalStateException("No available copies to borrow.");
        }
        availableCopies--;
    }

    /**
     * Updates the availability of the book when a copy is returned.
     */
    public void returnBook() {
        if (availableCopies >= totalCopies) {
            throw new IllegalStateException("All copies are already returned.");
        }
        availableCopies++;
    }

    /**
     * Checks if the book is currently available.
     *
     * @return True if available, otherwise false.
     */
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return isbn.equals(inventory.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "isbn='" + isbn + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                '}';
    }
}

