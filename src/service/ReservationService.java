package service;

import model.Book;
import model.Patron;
import dao.LoanDAO;

public class ReservationService {
    
    private LoanDAO loanDAO;

    public ReservationService(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    public void reserveBook(Book book, Patron patron) {
        if (loanDAO.isBookCheckedOut(book)) {
            throw new IllegalStateException("Book is already checked out.");
        }
        loanDAO.reserveBook(book, patron);
    }
}
