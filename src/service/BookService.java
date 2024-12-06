package service;

import dao.BookDAO;
import model.Book;
import util.SearchUtil;

import java.util.List;
import java.util.Optional;

/**
 * BookService handles book-related operations, including adding, removing, updating,
 * and searching books. Designed for extensibility and defensive programming.
 */
public class BookService {

    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        if (bookDAO == null) {
            throw new IllegalArgumentException("BookDAO cannot be null.");
        }
        this.bookDAO = bookDAO;
    }

    /**
     * Adds a new book to the library system.
     * @param book The book to add.
     * @throws IllegalArgumentException if the book is null or contains invalid data.
     */
    public void addBook(Book book) {
        if (book == null || !isValidBook(book)) {
            throw new IllegalArgumentException("Invalid book data.");
        }
        bookDAO.save(book);
    }

    /**
     * Removes a book from the library system by its ID.
     * @param bookId The ID of the book to remove.
     * @throws IllegalArgumentException if the bookId is invalid or the book does not exist.
     */
    public void removeBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid book ID.");
        }

        Optional<Book> book = bookDAO.findById(bookId);
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " does not exist.");
        }

        bookDAO.delete(bookId);
    }

    /**
     * Updates the details of an existing book.
     * @param book The book with updated details.
     * @throws IllegalArgumentException if the book is null, invalid, or does not exist.
     */
    public void updateBook(Book book) {
        if (book == null || !isValidBook(book)) {
            throw new IllegalArgumentException("Invalid book data.");
        }

        Optional<Book> existingBook = bookDAO.findById(book.getIsbn());
        if (existingBook.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + book.getIsbn() + " does not exist.");
        }

        bookDAO.update(book);
    }

    /**
     * Searches for books by a keyword in the title or author.
     * @param keyword The search keyword.
     * @return A list of books matching the search criteria.
     * @throws IllegalArgumentException if the keyword is null or empty.
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty.");
        }

        List<Book> allBooks = bookDAO.findAll();
        return SearchUtil.searchBooksByISBN(allBooks, keyword);
    }

    /**
     * Validates the book's data.
     * @param book The book to validate.
     * @return True if the book data is valid; otherwise, false.
     */
    private boolean isValidBook(Book book) {
        return book.getIsbn() != null && !book.getIsbn().trim().isEmpty()
                && book.getTitle() != null && !book.getTitle().trim().isEmpty()
                && book.getAuthor() != null && !book.getAuthor().trim().isEmpty();
    }
}
