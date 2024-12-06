package test.service;

import static org.mockito.Mockito.*;
//import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import dao.LoanDAO;
import service.ReservationService;
import model.Book;
import model.Patron;
import model.PatronType;

public class ReservationServiceTest {

    private LoanDAO loanDAO;
    private ReservationService reservationService;
    private Book book;
    private Patron patron;

    @Before
    public void setUp() {
        // Mock the LoanDAO
        loanDAO = mock(LoanDAO.class);
        reservationService = new ReservationService(loanDAO);

        // Setup a mock book and patron using the correct constructor
        book = new Book("Effective Java", "Joshua Bloch", "Programming", "1234567890123", 2008, 5);
        patron = new Patron("John Doe", "12345", PatronType.REGULAR);
    }

    // Test for successfully reserving a book
    @Test
    public void testReserveBookSuccess() {
        // Mock LoanDAO behavior
        when(loanDAO.isBookCheckedOut(book)).thenReturn(false); // Book is not checked out

        // Call the method under test
        reservationService.reserveBook(book, patron);

        // Verify interactions with LoanDAO
        verify(loanDAO).isBookCheckedOut(book);
        verify(loanDAO).reserveBook(book, patron);
    }

    // Test for attempting to reserve a book that is already checked out (should throw exception)
    @Test(expected = IllegalStateException.class)
    public void testReserveBookAlreadyCheckedOut() {
        // Mock LoanDAO behavior
        when(loanDAO.isBookCheckedOut(book)).thenReturn(true); // Book is already checked out

        // Call the method under test, which should throw an exception
        reservationService.reserveBook(book, patron);
    }

    // Test for invalid input: reserve with null book
    @Test(expected = IllegalArgumentException.class)
    public void testReserveBookNullBook() {
        reservationService.reserveBook(null, patron); // Should throw an exception
    }

    // Test for invalid input: reserve with null patron
    @Test(expected = IllegalArgumentException.class)
    public void testReserveBookNullPatron() {
        reservationService.reserveBook(book, null); // Should throw an exception
    }

    // Test for invalid input: reserve with both null book and patron
    @Test(expected = IllegalArgumentException.class)
    public void testReserveBookNullBookAndPatron() {
        reservationService.reserveBook(null, null); // Should throw an exception
    }

    // Test for invalid input: null loanDAO (when creating the service)
    @Test(expected = IllegalArgumentException.class)
    public void testReserveBookNullLoanDAO() {
        ReservationService serviceWithNullDAO = new ReservationService(null); // LoanDAO is null
        serviceWithNullDAO.reserveBook(book, patron); // Should throw an exception
    }
}
