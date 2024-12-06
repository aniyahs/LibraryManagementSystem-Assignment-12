package dao;

import model.Book;

import java.util.List;
import java.util.Optional;

/**
 * BookDAO defines the interface for accessing and managing book data.
 */
public interface BookDAO {

    /**
     * Saves a new book to the data source.
     * @param book The book to save.
     */
    void save(Book book);

    /**
     * Deletes a book from the data source by its ID.
     * @param bookId The ID of the book to delete.
     */
    void delete(String bookId);

    /**
     * Updates an existing book in the data source.
     * @param book The updated book.
     */
    void update(Book book);

    /**
     * Finds a book by its ID.
     * @param bookId The ID of the book to find.
     * @return An Optional containing the found book, or empty if not found.
     */
    Optional<Book> findById(String bookId);

    /**
     * Retrieves all books from the data source.
     * @return A list of all books.
     */
    List<Book> findAll();
}
