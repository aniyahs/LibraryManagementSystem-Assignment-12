package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Book;

class BookTest {

    private Book validBook;

    @Before
    void setUp() {
        // Setting up a valid Book object for reuse in multiple tests
        validBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
    }

    @Test
    void testValidBookCreation() {
        assertNotNull(validBook);
        assertEquals("The Great Gatsby", validBook.getTitle());
        assertEquals("F. Scott Fitzgerald", validBook.getAuthor());
        assertEquals("Fiction", validBook.getGenre());
        assertEquals("9780743273565", validBook.getIsbn());
        assertEquals(1925, validBook.getPublicationYear());
        assertEquals(5, validBook.getTotalCopies());
        assertEquals(5, validBook.getAvailableCopies());
    }

    @Test
    void testBookBorrow() {
        assertTrue(validBook.borrowBook());
        assertEquals(4, validBook.getAvailableCopies());
    }

    @Test
    void testBookReturn() {
        validBook.borrowBook(); // Borrowing first
        assertTrue(validBook.returnBook());
        assertEquals(5, validBook.getAvailableCopies());
    }

    @Test
    void testBorrowBookThrowsExceptionWhenNoCopiesAvailable() {
        // Borrow all copies
        for (int i = 0; i < 5; i++) {
            validBook.borrowBook();
        }
        assertThrows(IllegalStateException.class, () -> validBook.borrowBook());
    }

    @Test
    void testReturnBookThrowsExceptionWhenAllCopiesReturned() {
        // Return all copies
        validBook.returnBook();
        validBook.returnBook();
        validBook.returnBook();
        validBook.returnBook();
        assertThrows(IllegalStateException.class, () -> validBook.returnBook());
    }

    // Boundary tests for creating a book with invalid data
    @Test
    void testBookCreationWithNullTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Author", "Genre", "12345", 2020, 5));
    }

    @Test
    void testBookCreationWithEmptyTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book(" ", "Author", "Genre", "12345", 2020, 5));
    }

    @Test
    void testBookCreationWithNegativePublicationYearThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", "12345", -2020, 5));
    }

    @Test
    void testBookCreationWithNegativeTotalCopiesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", "12345", 2020, -5));
    }

    @Test
    void testBookCreationWithInvalidIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", null, 2020, 5));
    }

    @Test
    void testBookCreationWithEmptyIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", " ", 2020, 5));
    }

    // Testing equality
    @Test
    void testBookEquality() {
        Book anotherBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
        assertEquals(validBook, anotherBook);
    }

    @Test
    void testBookEqualityWithDifferentIsbn() {
        Book differentBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "1234567890123", 1925, 5);
        assertNotEquals(validBook, differentBook);
    }

    // Testing toString method
    @Test
    void testToString() {
        String expectedString = "Book{title='The Great Gatsby', author='F. Scott Fitzgerald', genre='Fiction', isbn='9780743273565', publicationYear=1925, totalCopies=5, availableCopies=5}";
        assertEquals(expectedString, validBook.toString());
    }

    // Testing getter and setter for available copies
    @Test
    void testSetAvailableCopies() {
        validBook.setTotalCopies(10);
        assertEquals(10, validBook.getTotalCopies());
        assertEquals(10, validBook.getAvailableCopies());
    }
}
