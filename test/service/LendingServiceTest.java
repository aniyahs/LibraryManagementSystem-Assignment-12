package test.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import model.Book;
import model.Loan;
import model.Patron;
import model.PatronType;
import dao.LoanDAO;
import dao.ReservationDAO;
import service.LendingService;
import model.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public class LendingServiceTest {

    private LoanDAO loanDAO;
    private ReservationDAO reservationDAO;
    private LendingService lendingService;
    private Book validBook;
    private Patron patron;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private Loan validLoan;

    @Before
    public void setUp() {
        loanDAO = Mockito.mock(LoanDAO.class);
        reservationDAO = Mockito.mock(ReservationDAO.class);
        lendingService = new LendingService(loanDAO, reservationDAO);

        validBook = new Book("Effective Java", "Joshua Bloch", "Programming", "978-0134685991", 2018, 5);
        patron = new Patron("John Doe", "12345", PatronType.PREMIUM);

        loanDate = LocalDate.now();
        dueDate = loanDate.plusWeeks(2);
        validLoan = new Loan(validBook, patron, loanDate, dueDate);
    }

    @Test
    public void testCheckoutBookValid() {
        Mockito.when(loanDAO.isBookCheckedOut(validBook)).thenReturn(false);
        lendingService.checkoutBook(validBook, patron, loanDate, dueDate);
        Mockito.verify(loanDAO).save(Mockito.any(Loan.class));
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckoutBookAlreadyCheckedOut() {
        Mockito.when(loanDAO.isBookCheckedOut(validBook)).thenReturn(true);
        lendingService.checkoutBook(validBook, patron, loanDate, dueDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckoutBookNullBook() {
        lendingService.checkoutBook(null, patron, loanDate, dueDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckoutBookNullPatron() {
        lendingService.checkoutBook(validBook, null, loanDate, dueDate);
    }

    @Test
    public void testReturnBookValid() {
    // Instead of using Mockito.when(), just verify that the update method is called
    lendingService.returnBook(validLoan, LocalDate.now());
    
    // Verify that update() is called once with the validLoan
    Mockito.verify(loanDAO).update(validLoan);
}


    @Test(expected = IllegalStateException.class)
    public void testReturnBookAlreadyReturned() {
        validLoan.setReturnDate(LocalDate.now()); // Already returned
        lendingService.returnBook(validLoan, LocalDate.now());
    }

    @Test
    public void testReserveBookValid() {
        Mockito.when(loanDAO.isBookCheckedOut(validBook)).thenReturn(true);
        Mockito.when(reservationDAO.findByBookAndPatron(validBook, patron)).thenReturn(Optional.empty());

        // Creating the Reservation using the proper constructor
        Reservation reservation = new Reservation(validBook, patron);
        lendingService.reserveBook(validBook, patron);
        Mockito.verify(reservationDAO).save(reservation);
    }

    @Test(expected = IllegalStateException.class)
    public void testReserveBookAlreadyReserved() {
        Mockito.when(loanDAO.isBookCheckedOut(validBook)).thenReturn(true);
        Mockito.when(reservationDAO.findByBookAndPatron(validBook, patron)).thenReturn(Optional.of(new Reservation(validBook, patron)));
        lendingService.reserveBook(validBook, patron);
    }

    @Test(expected = IllegalStateException.class)
    public void testReserveBookNotCheckedOut() {
        Mockito.when(loanDAO.isBookCheckedOut(validBook)).thenReturn(false);
        lendingService.reserveBook(validBook, patron);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateLateFeeNullLoan() {
        lendingService.calculateLateFee(null, 1.0);
    }

    @Test
    public void testCalculateLateFeeValid() {
        Mockito.when(validLoan.calculateLateFee(1.0)).thenReturn(5.0);
        double lateFee = lendingService.calculateLateFee(validLoan, 1.0);
        assertEquals(5.0, lateFee, 0.0);
    }
}
