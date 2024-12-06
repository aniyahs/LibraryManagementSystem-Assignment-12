package service;

import dao.LoanDAO;
import model.Patron;
import model.Loan;

import java.util.List;

public class NotificationService {

    private LoanDAO lendingDAO;

    public NotificationService(LoanDAO lendingDAO) {
        this.lendingDAO = lendingDAO;
    }

    // Notify patron of overdue books
    public void notifyOverdueBooks(Patron patron) {
        List<Loan> loans = lendingDAO.findLoansByPatron(patron); // Assuming a method to get loans for a patron

        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                // Send notification (this could be email, SMS, etc.)
                System.out.println("Patron " + patron.getName() + ", your loan for book " +
                        loan.getBook().getTitle() + " is overdue. Please return it ASAP.");
            }
        }
    }

    // Sends a notification to a patron when a reserved book becomes available
    public void sendReservationAvailableNotification(Patron patron, Loan loan) {
        // Check if the book is available and notify the patron
        sendNotification(patron, "The book '" + loan.getBook().getTitle() + "' is now available for pickup.");
    }

    // private void sendOverdueNotification(Patron patron, Loan loan) {
    //     // Generate overdue message
    //     String message = "Your loan for the book '" + loan.getBook().getTitle() + "' is overdue. Please return it as soon as possible.";
    //     sendNotification(patron, message);
    // }

    private void sendNotification(Patron patron, String message) {
        // Simulate sending notification (email, SMS, etc.)
        System.out.println("Sending notification to " + patron.getName() + ": " + message);
    }

}
