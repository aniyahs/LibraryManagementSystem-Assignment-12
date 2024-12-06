package dao;

import model.Book;
import model.Patron;
import model.Reservation;

import java.util.Optional;

public interface ReservationDAO {

    void save(Reservation reservation);

    Optional<Reservation> findByBookAndPatron(Book book, Patron patron);
}
