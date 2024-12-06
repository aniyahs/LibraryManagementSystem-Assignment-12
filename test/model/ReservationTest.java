package test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Book;
import model.Patron;
import model.PatronType;
import model.Reservation;

public class ReservationTest {

    private Book book;
    private Patron patron;
    private Reservation reservation;

    @Before
    public void setUp() {
        book = new Book("Effective Java", "Joshua Bloch", "Programming", "978-0134685991", 2018, 5);
        patron = new Patron("Alice Johnson", "P001", PatronType.REGULAR);
        reservation = new Reservation(book, patron);
    }

    @Test
    public void testReservationConstructorValid() {
        assertNotNull(reservation);
        assertEquals(book, reservation.getBook());
        assertEquals(patron, reservation.getPatron());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReservationConstructorNullBook() {
        new Reservation(null, patron);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReservationConstructorNullPatron() {
        new Reservation(book, null);
    }

    @Test
    public void testEqualsAndHashCode() {
        Reservation sameReservation = new Reservation(book, patron);
        assertTrue(reservation.equals(sameReservation));
        assertEquals(reservation.hashCode(), sameReservation.hashCode());

        Reservation differentReservation = new Reservation(
            new Book("Clean Code", "Robert C. Martin", "Programming", "978-0132350884", 2008, 3), patron);
        assertFalse(reservation.equals(differentReservation));
        assertNotEquals(reservation.hashCode(), differentReservation.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Reservation{book=Effective Java, patron=Alice Johnson}";
        assertEquals(expectedString, reservation.toString());
    }
}
