package service;

import model.Book;
import model.Loan;
import model.Patron;
import dao.LoanDAO;
import java.time.LocalDate;
import java.util.Optional;

import dao.ReservationDAO;
import model.Reservation;

public class LendingService {

    private LoanDAO loanDAO;
    private ReservationDAO reservationDAO;

    public LendingService(LoanDAO loanDAO, ReservationDAO reservationDAO) {
        this.loanDAO = loanDAO;
        this.reservationDAO = reservationDAO;
    }

    // Checkout a book for a patron
    public void checkoutBook(Book book, Patron patron, LocalDate loanDate, LocalDate dueDate) {
        validateBookAndPatron(book, patron);
        if (loanDAO.isBookCheckedOut(book)) {
            throw new IllegalStateException("Book is already checked out.");
        }
        Loan loan = new Loan(book, patron, loanDate, dueDate);
        loanDAO.save(loan);
    }

    // Return a book
    public void returnBook(Loan loan, LocalDate returnDate) {
        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned.");
        }
        loan.setReturnDate(returnDate);
        loanDAO.update(loan);
    }

    // Reserve a book for a patron
    public void reserveBook(Book book, Patron patron) {
        validateBookAndPatron(book, patron);
        if (isBookCheckedOut(book)) {
            // Check if the book is already reserved
            Optional<Reservation> existingReservation = reservationDAO.findByBookAndPatron(book, patron);
            if (existingReservation.isPresent()) {
                throw new IllegalStateException("You already have a reservation for this book.");
            }
            reservationDAO.save(new Reservation(book, patron));
        } else {
            throw new IllegalStateException("Book is available, no need for reservation.");
        }
    }

    // Check if a book is checked out
    public boolean isBookCheckedOut(Book book) {
        return loanDAO.isBookCheckedOut(book);
    }

    // Calculate the late fee for a loan
    public double calculateLateFee(Loan loan, double dailyLateFee) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        return loan.calculateLateFee(dailyLateFee);
    }

    // Validate the book and patron before any transaction
    private void validateBookAndPatron(Book book, Patron patron) {
        if (book == null || patron == null) {
            throw new IllegalArgumentException("Book and Patron cannot be null.");
        }
    }
}
