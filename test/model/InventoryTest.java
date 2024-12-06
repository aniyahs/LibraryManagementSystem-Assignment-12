package test.modelTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Inventory;

public class InventoryTest {

    private Inventory inventory;

    @Before
    public void setUp() {
        // Setting up a valid Inventory object for testing
        inventory = new Inventory("9780743273565", 10, 5);
    }

    @Test
    public void testInventoryCreation() {
        assertNotNull(inventory);
        assertEquals("9780743273565", inventory.getIsbn());
        assertEquals(10, inventory.getTotalCopies());
        assertEquals(5, inventory.getAvailableCopies());
    }

    @Test
    public void testSetTotalCopies() {
        inventory.setTotalCopies(12);
        assertEquals(12, inventory.getTotalCopies());
        assertEquals(5, inventory.getAvailableCopies());
    }

    @Test
    public void testSetTotalCopiesThrowsExceptionWhenLessThanAvailableCopies() {
        assertThrows(IllegalArgumentException.class, () -> inventory.setTotalCopies(4));
    }

    @Test
    public void testBorrowBook() {
        inventory.borrowBook();
        assertEquals(4, inventory.getAvailableCopies());
    }

    @Test
    public void testBorrowBookThrowsExceptionWhenNoCopiesAvailable() {
        for (int i = 0; i < 5; i++) {
            inventory.borrowBook();
        }
        assertThrows(IllegalStateException.class, () -> inventory.borrowBook());
    }

    @Test
    public void testReturnBook() {
        inventory.borrowBook(); // Borrow a book first
        inventory.returnBook();
        assertEquals(5, inventory.getAvailableCopies());
    }

    @Test
    public void testReturnBookThrowsExceptionWhenAllCopiesReturned() {
        for (int i = 0; i < 5; i++) {
            inventory.returnBook();
        }
        assertThrows(IllegalStateException.class, () -> inventory.returnBook());
    }

    @Test
    public void testIsAvailable() {
        assertTrue(inventory.isAvailable());
        for (int i = 0; i < 5; i++) {
            inventory.borrowBook();
        }
        assertFalse(inventory.isAvailable());
    }

    @Test
    public void testEquals() {
        Inventory sameInventory = new Inventory("9780743273565", 8, 4);
        assertEquals(inventory, sameInventory);

        Inventory differentInventory = new Inventory("1234567890123", 10, 5);
        assertNotEquals(inventory, differentInventory);
    }

    @Test
    public void testToString() {
        String expected = "Inventory{isbn='9780743273565', totalCopies=10, availableCopies=5}";
        assertEquals(expected, inventory.toString());
    }
}
