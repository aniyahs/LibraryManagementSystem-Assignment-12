package test.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import dao.PatronDAO;
import service.PatronService;
import model.Patron;
import model.PatronType;

public class PatronServiceTest {

    private PatronDAO patronDAO;
    private PatronService patronService;
    private Patron patron;

    @Before
    public void setUp() {
        // Mock the PatronDAO
        patronDAO = mock(PatronDAO.class);
        patronService = new PatronService(patronDAO);

        // Setup a mock patron
        patron = new Patron("John Doe", "12345", PatronType.REGULAR);
    }

    // Test for registering a new patron successfully
    @Test
    public void testRegisterPatronSuccess() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.empty()); // No existing patron

        // Call the method under test
        patronService.registerPatron(patron);

        // Verify interactions with PatronDAO
        verify(patronDAO).findById(patron.getPatronId());
        verify(patronDAO).save(patron);
    }

    // Test for attempting to register an existing patron (should throw exception)
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterPatronAlreadyExists() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.of(patron)); // Patron already exists

        // Call the method under test, which should throw an exception
        patronService.registerPatron(patron);
    }

    // Test for updating an existing patron successfully
    @Test
    public void testUpdatePatronSuccess() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.of(patron)); // Patron exists

        // Call the method under test
        patronService.updatePatron(patron);

        // Verify interactions with PatronDAO
        verify(patronDAO).findById(patron.getPatronId());
        verify(patronDAO).update(patron);
    }

    // Test for attempting to update a non-existing patron (should throw exception)
    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePatronNotFound() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.empty()); // Patron does not exist

        // Call the method under test, which should throw an exception
        patronService.updatePatron(patron);
    }

    // Test for retrieving a patron by ID successfully
    @Test
    public void testGetPatronByIdSuccess() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.of(patron)); // Patron exists

        // Call the method under test
        Patron retrievedPatron = patronService.getPatronById(patron.getPatronId());

        // Verify the returned patron
        assertNotNull(retrievedPatron);
        assertEquals(patron.getPatronId(), retrievedPatron.getPatronId());
    }

    // Test for attempting to retrieve a patron by ID when the patron does not exist (should throw exception)
    @Test(expected = IllegalArgumentException.class)
    public void testGetPatronByIdNotFound() {
        // Mock PatronDAO behavior
        when(patronDAO.findById(patron.getPatronId())).thenReturn(Optional.empty()); // Patron does not exist

        // Call the method under test, which should throw an exception
        patronService.getPatronById(patron.getPatronId());
    }

    // Test for retrieving all patrons
    @Test
    public void testGetAllPatrons() {
        // Mock PatronDAO behavior
        List<Patron> patronList = Arrays.asList(patron);
        when(patronDAO.findAll()).thenReturn(patronList);

        // Call the method under test
        List<Patron> patrons = patronService.getAllPatrons();

        // Verify the result
        assertNotNull(patrons);
        assertEquals(1, patrons.size());
        assertEquals(patron, patrons.get(0));
    }

    // Test for invalid input: register with null patron
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterPatronNull() {
        patronService.registerPatron(null); // Should throw an exception
    }

    // Test for invalid input: update with null patron
    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePatronNull() {
        patronService.updatePatron(null); // Should throw an exception
    }

    // Test for invalid input: get patron by null ID
    @Test(expected = IllegalArgumentException.class)
    public void testGetPatronByIdNull() {
        patronService.getPatronById(null); // Should throw an exception
    }

    // Test for invalid input: get all patrons with null patronDAO
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllPatronsNull() {
        PatronService serviceWithNullDAO = new PatronService(null); // PatronDAO is null
        serviceWithNullDAO.getAllPatrons(); // Should throw an exception
    }
}
