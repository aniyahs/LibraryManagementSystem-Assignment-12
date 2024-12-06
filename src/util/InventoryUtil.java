package util;

import model.Book;
//import model.Inventory;
//import model.Loan;
import dao.InventoryDAO;

import java.util.List;

public class InventoryUtil {

    private InventoryDAO inventoryDAO;

    public InventoryUtil(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * Adds a new book to the inventory.
     *
     * @param book The book to add.
     */
    public void addBookToInventory(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        inventoryDAO.save(book); // Save book to the inventory database
    }

    /**
     * Removes a book from the inventory.
     *
     * @param book The book to remove.
     */
    public void removeBookFromInventory(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        inventoryDAO.delete(book.getIsbn()); // Delete book by ISBN
    }

    /**
     * Updates the availability status of a book in the inventory.
     *
     * @param book The book whose availability status is to be updated.
     * @param isAvailable The new availability status.
     */
    public void updateBookAvailability(Book book, boolean isAvailable) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        book.setAvailable(isAvailable);  // Set the availability status
        inventoryDAO.update(book);       // Update book in the inventory database
    }

    /**
     * Retrieves the list of all books in the inventory.
     *
     * @return The list of books in the inventory.
     */
    public List<Book> getAllBooks() {
        return inventoryDAO.findAll(); // Retrieve all books from inventory
    }
}
