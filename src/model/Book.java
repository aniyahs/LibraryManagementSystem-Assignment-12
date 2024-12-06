package model;

import java.util.Objects;

/**
 * Represents a book in the library.
 * This class follows defensive programming principles and ensures extensibility.
 */
public class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;

    /**
     * Constructor for creating a Book object.
     *
     * @param title           The title of the book (non-null, non-empty).
     * @param author          The author of the book (non-null, non-empty).
     * @param genre           The genre of the book (nullable).
     * @param isbn            The ISBN of the book (non-null, non-empty).
     * @param publicationYear The year the book was published (> 0).
     * @param totalCopies     The total number of copies of the book in the library (>= 0).
     */
    public Book(String title, String author, String genre, String isbn, int publicationYear, int totalCopies) {
        validateString(title, "Title");
        validateString(author, "Author");
        validateString(isbn, "ISBN");
        validatePositive(publicationYear, "Publication Year");
        validateNonNegative(totalCopies, "Total Copies");

        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Initially, all copies are available.
    }

    // Getters and Setters with defensive validation
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateString(title, "Title");
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        validateString(author, "Author");
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre; // Genre can be nullable, no validation required.
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        validateString(isbn, "ISBN");
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        validatePositive(publicationYear, "Publication Year");
        this.publicationYear = publicationYear;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        validateNonNegative(totalCopies, "Total Copies");
        this.totalCopies = totalCopies;
        syncAvailableCopies(); // Ensure consistency with available copies.
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // Borrowing and returning books
    public boolean borrowBook() {
        if (availableCopies <= 0) {
            throw new IllegalStateException("No copies available to borrow.");
        }
        availableCopies--;
        return true;
    }

    public boolean returnBook() {
        if (availableCopies >= totalCopies) {
            throw new IllegalStateException("All copies are already returned.");
        }
        availableCopies++;
        return true;
    }

    // Private utility methods for validation and synchronization
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    private void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0.");
        }
    }

    private void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    private void syncAvailableCopies() {
        if (availableCopies > totalCopies) {
            availableCopies = totalCopies;
        } else if (availableCopies < totalCopies) {
            availableCopies = totalCopies;
        }
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn); // ISBN uniquely identifies a book.
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                '}';
    }

    public void setAvailable(boolean isAvailable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }
}
