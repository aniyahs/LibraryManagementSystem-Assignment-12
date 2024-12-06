package service;

import model.Loan;
import dao.LoanDAO;
import java.time.LocalDate;

public class ReturnService {

    private LoanDAO loanDAO;

    public ReturnService(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    public void returnBook(Loan loan, LocalDate returnDate) {
        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned.");
        }
        loan.setReturnDate(returnDate);
        loanDAO.update(loan);
    }
}
