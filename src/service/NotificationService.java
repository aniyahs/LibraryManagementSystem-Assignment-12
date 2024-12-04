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
}
