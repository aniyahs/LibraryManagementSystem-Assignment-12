import model.Book;

public class Main {

    public static void main(String[] args) {
        // Create a new Book instance
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);

        // Print the details of the book
        System.out.println("Book Details: ");
        System.out.println(book);

        // Test the get and set methods
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Total Copies: " + book.getTotalCopies());
        System.out.println("Available Copies: " + book.getAvailableCopies());

        // Borrow a book
        System.out.println("\nBorrowing a book...");
        book.borrowBook();
        System.out.println("Available Copies after borrowing: " + book.getAvailableCopies());

        // Return the book
        System.out.println("\nReturning the book...");
        book.returnBook();
        System.out.println("Available Copies after returning: " + book.getAvailableCopies());

        // Try borrowing a book when no copies are available
        try {
            System.out.println("\nAttempting to borrow a book with no copies left...");
            for (int i = 0; i < 5; i++) {
                book.borrowBook();
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Try returning a book when all copies are returned
        try {
            System.out.println("\nAttempting to return a book when all copies are returned...");
            book.returnBook();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
