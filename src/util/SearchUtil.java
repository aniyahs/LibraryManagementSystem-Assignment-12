package util;

import model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchUtil {

    /**
     * Searches for books by ISBN (exact match).
     *
     * @param books The list of books to search in.
     * @param isbn  The ISBN to search for (exact match).
     * @return A list of books that match the ISBN.
     */
    public static List<Book> searchBooksByISBN(List<Book> allBooks, String isbn) {
        return allBooks.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    // Other search methods...
}
