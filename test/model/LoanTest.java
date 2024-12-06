package test.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Book;
import model.Loan;
import model.Patron;
import model.PatronType;

import java.time.LocalDate;

public class LoanTest {

    private Loan loan;
    private Book book;
    private Patron patron;

    @Before
    public void setUp() {
        book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925, 5);
        patron = new Patron("John Doe", "P001", PatronType.REGULAR);
        loan = new Loan(book, patron, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15));
    }

    @Test
    public void testLoanCreation() {
        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(patron, loan.getPatron());
        assertEquals(LocalDate.of(2024, 12, 1), loan.getLoanDate());
        assertEquals(LocalDate.of(2024, 12, 15), loan.getDueDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    public void testSetReturnDateValid() {
        loan.setReturnDate(LocalDate.of(2024, 12, 10));
        assertEquals(LocalDate.of(2024, 12, 10), loan.getReturnDate());
    }

    @Test
    public void testSetReturnDateBeforeLoanDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loan.setReturnDate(LocalDate.of(2024, 11, 30)));
    }

    @Test
    public void testIsOverdueWithNoReturnDate() {
        Loan overdueLoan = new Loan(book, patron, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 15));
        assertTrue(overdueLoan.isOverdue());
    }

    @Test
    public void testIsOverdueWithReturnDate() {
        loan.setReturnDate(LocalDate.of(2024, 12, 16));
        assertTrue(loan.isOverdue());
    }

    @Test
    public void testIsNotOverdueWithReturnDateOnOrBeforeDueDate() {
        loan.setReturnDate(LocalDate.of(2024, 12, 15));
        assertFalse(loan.isOverdue());
    }

    @Test
    public void testCalculateLateFeeNoOverdue() {
        double fee = loan.calculateLateFee(2.0);
        assertEquals(0.0, fee, 0.01);
    }

    @Test
    public void testCalculateLateFeeWithOverdue() {
        loan.setReturnDate(LocalDate.of(2024, 12, 20));
        double fee = loan.calculateLateFee(2.0);
        assertEquals(10.0, fee, 0.01);
    }

    @Test
    public void testCalculateLateFeeThrowsExceptionForNegativeDailyFee() {
        assertThrows(IllegalArgumentException.class, () -> loan.calculateLateFee(-1.0));
    }

    @Test
    public void testGetLoanId() {
        String expectedLoanId = "9780743273565-P001-2024-12-01";
        assertEquals(expectedLoanId, loan.getLoanId());
    }

    @Test
    public void testEqualsAndHashCode() {
        Loan sameLoan = new Loan(book, patron, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15));
        assertEquals(loan, sameLoan);
        assertEquals(loan.hashCode(), sameLoan.hashCode());

        Loan differentLoan = new Loan(book, new Patron("Jane Doe", "P002", PatronType.REGULAR), LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15));
        assertNotEquals(loan, differentLoan);
    }

    @Test
    public void testToString() {
        String expected = "Loan{book=The Great Gatsby, patron=John Doe, loanDate=2024-12-01, dueDate=2024-12-15, returnDate=null}";
        assertEquals(expected, loan.toString());
    }
}
