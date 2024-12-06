package dao;

import model.Book;
import model.Loan;
import model.Patron;

import java.util.List;
import java.util.Optional;

/**
 * LendingDAO defines the interface for managing lending records.
 */
public interface LoanDAO {

    void save(Loan record);

    void delete(String recordId);

    void update(Loan record);

    Optional<Loan> findById(String recordId);

    List<Loan> findAll();

    boolean isBookCheckedOut(Book book);

    List<Loan> findLoansByPatron(Patron patron);

    void reserveBook(Book book, Patron patron);
}
