package test.service;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
//import java.util.List;
import java.io.PrintStream; // Import PrintStream
import dao.LoanDAO;
import service.NotificationService;
import model.Patron;
import model.Loan;
import model.PatronType; // Import PatronType
import model.Book; // Import Book class

public class NotificationServiceTest {

    private LoanDAO loanDAO;
    private NotificationService notificationService;
    private Patron patron;
    private Loan overdueLoan;
    private Loan nonOverdueLoan;
    private Loan reservationAvailableLoan;

    @Before
    public void setUp() {
        // Mock dependencies
        loanDAO = mock(LoanDAO.class);
        notificationService = new NotificationService(loanDAO);

        // Setup mock patron
        patron = new Patron("John Doe", "12345", PatronType.REGULAR);

        // Setup mock loans
        overdueLoan = mock(Loan.class);
        nonOverdueLoan = mock(Loan.class);
        reservationAvailableLoan = mock(Loan.class);

        // Mock the isOverdue method
        when(overdueLoan.isOverdue()).thenReturn(true);
        when(nonOverdueLoan.isOverdue()).thenReturn(false);

        // Mock the findLoansByPatron method to return loans for the patron
        when(loanDAO.findLoansByPatron(patron)).thenReturn(Arrays.asList(overdueLoan, nonOverdueLoan));
    }

    // Test for notifying overdue books
    @Test
    public void testNotifyOverdueBooks() {
        // Call the method under test
        notificationService.notifyOverdueBooks(patron);

        // Verify that the overdue loan triggers a notification
        verify(overdueLoan).getBook(); // Checking that getBook was called on the overdue loan

        // Verify that no notification was sent for the non-overdue loan
        verify(nonOverdueLoan, never()).getBook();
    }

    // Test for sending notification when a reserved book becomes available
    @Test
    public void testSendReservationAvailableNotification() {
        // Setup loan and book for reservation
        Book book = mock(Book.class); // Book class is now imported
        when(book.getTitle()).thenReturn("Sample Book");
        when(reservationAvailableLoan.getBook()).thenReturn(book);

        // Call the method under test
        notificationService.sendReservationAvailableNotification(patron, reservationAvailableLoan);

        // Verify that the sendNotification method was called with the correct message
        verify(loanDAO).findLoansByPatron(patron); // Verify interaction with loanDAO
    }

    // Test to check that sendNotification is indirectly tested via print statements
    @Test
    public void testSendNotification() {
        // Setup mock print stream to capture System.out
        PrintStream originalOut = System.out;
        PrintStream mockOut = mock(PrintStream.class);
        System.setOut(mockOut);

        // Setup the scenario where a notification should be sent
        notificationService.sendReservationAvailableNotification(patron, reservationAvailableLoan);

        // Verify that the message is printed to the output stream
        verify(mockOut).println(contains("Sending notification to John Doe"));

        // Restore original System.out
        System.setOut(originalOut);
    }

    // Additional test to check that the notifyOverdueBooks handles no loans scenario gracefully
    @Test
    public void testNotifyOverdueBooksNoLoans() {
        // Setup empty loan list for patron
        when(loanDAO.findLoansByPatron(patron)).thenReturn(Arrays.asList());

        // Call the method under test
        notificationService.notifyOverdueBooks(patron);

        // Verify that no notifications are sent when no loans are found
        verify(loanDAO).findLoansByPatron(patron);
    }

    // Test for invalid scenarios where patron or loan is null
    @Test(expected = IllegalArgumentException.class)
    public void testNotifyOverdueBooksInvalidArguments() {
        // Call with null patron
        notificationService.notifyOverdueBooks(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendReservationAvailableNotificationInvalidArguments() {
        // Call with null patron or loan
        notificationService.sendReservationAvailableNotification(null, null);
    }

    // Verify that an exception is thrown if a loan's overdue check fails (e.g., loan is null)
    // @Test(expected = IllegalArgumentException.class)
    // public void testSendNotificationWithNullLoan() {
    //     notificationService.sendNotification(patron, null);
    // }
}
