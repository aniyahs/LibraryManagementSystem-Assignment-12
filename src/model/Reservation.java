package model;

import java.util.Objects;

public class Reservation {

    private Book book;
    private Patron patron;

    public Reservation(Book book, Patron patron) {
        if (book == null || patron == null) {
            throw new IllegalArgumentException("Book and Patron cannot be null.");
        }
        this.book = book;
        this.patron = patron;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(book, that.book) &&
               Objects.equals(patron, that.patron);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, patron);
    }

    @Override
    public String toString() {
        return "Reservation{" +
               "book=" + book.getTitle() +
               ", patron=" + patron.getName() +
               '}';
    }
}
