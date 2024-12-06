package service;

import model.Book;
import model.Loan;
import model.Patron;
import dao.LoanDAO;
import java.time.LocalDate;

public class CheckoutService {
    
    private LoanDAO loanDAO;

    public CheckoutService(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    public void checkoutBook(Book book, Patron patron, LocalDate loanDate, LocalDate dueDate) {
        if (loanDAO.isBookCheckedOut(book)) {
            throw new IllegalStateException("Book is already checked out.");
        }
        Loan loan = new Loan(book, patron, loanDate, dueDate);
        loanDAO.save(loan);
    }
}
