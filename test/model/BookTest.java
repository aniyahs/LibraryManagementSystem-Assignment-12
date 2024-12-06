package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Book;

public class BookTest {

    public Book validBook;

    @Before
    public void setUp() {
        // Setting up a valid Book object for reuse in multiple tests
        validBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
    }

    @Test
    public void testValidBookCreation() {
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
    public void testBookBorrow() {
        assertTrue(validBook.borrowBook());
        assertEquals(4, validBook.getAvailableCopies());
    }

    @Test
    public void testBookReturn() {
        validBook.borrowBook(); // Borrowing first
        assertTrue(validBook.returnBook());
        assertEquals(5, validBook.getAvailableCopies());
    }

    @Test
    public void testBorrowBookThrowsExceptionWhenNoCopiesAvailable() {
        // Borrow all copies
        for (int i = 0; i < 5; i++) {
            validBook.borrowBook();
        }
        assertThrows(IllegalStateException.class, () -> validBook.borrowBook());
    }

    @Test
    public void testReturnBookThrowsExceptionWhenAllCopiesReturned() {
        assertThrows(IllegalStateException.class, () -> validBook.returnBook());
    }

    // Boundary tests for creating a book with invalid data
    @Test
    public void testBookCreationWithNullTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Author", "Genre", "12345", 2020, 5));
    }

    @Test
    public void testBookCreationWithEmptyTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book(" ", "Author", "Genre", "12345", 2020, 5));
    }

    @Test
    public void testBookCreationWithNegativePublicationYearThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", "12345", -2020, 5));
    }

    @Test
    public void testBookCreationWithNegativeTotalCopiesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", "12345", 2020, -5));
    }

    @Test
    public void testBookCreationWithInvalidIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", null, 2020, 5));
    }

    @Test
    public void testBookCreationWithEmptyIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", "Genre", " ", 2020, 5));
    }

    // Testing equality
    @Test
    public void testBookEquality() {
        Book anotherBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
        assertEquals(validBook, anotherBook);
    }

    @Test
    public void testBookEqualityWithDifferentIsbn() {
        Book differentBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "1234567890123", 1925, 5);
        assertNotEquals(validBook, differentBook);
    }

    // Testing hashCode
    @Test
    public void testHashCodeConsistency() {
        Book anotherBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
        assertEquals(validBook.hashCode(), anotherBook.hashCode());
    }

    @Test
    public void testHashCodeDifference() {
        Book differentBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "1234567890123", 1925, 5);
        assertNotEquals(validBook.hashCode(), differentBook.hashCode());
    }

    // Testing toString method
    @Test
    public void testToString() {
        String expectedString = "Book{title='The Great Gatsby', author='F. Scott Fitzgerald', genre='Fiction', isbn='9780743273565', publicationYear=1925, totalCopies=5, availableCopies=5}";
        assertEquals(expectedString, validBook.toString());
    }

    // Testing total and available copies synchronization
    @Test
    public void testSetTotalCopiesSyncsAvailableCopies() {
        validBook.setTotalCopies(10);
        assertEquals(10, validBook.getTotalCopies());
        assertEquals(10, validBook.getAvailableCopies());
    }

    @Test
    public void testSetTotalCopiesReducesAvailableCopiesIfExceeds() {
        validBook.borrowBook(); // Available copies = 4
        validBook.setTotalCopies(3); // Total copies < available copies
        assertEquals(3, validBook.getAvailableCopies());
    }

    // Ensuring unimplemented method throws exception
    @Test
    public void testSetAvailableThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> validBook.setAvailable(true));
    }
}
