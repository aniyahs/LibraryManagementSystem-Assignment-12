package service;

import model.Patron;
import dao.PatronDAO;
import java.util.List;

public class PatronService {

    private PatronDAO patronDAO;

    public PatronService(PatronDAO patronDAO) {
        this.patronDAO = patronDAO;
    }

    // Registers a new patron
    public void registerPatron(Patron patron) {
        validatePatron(patron);
        if (patronDAO.findById(patron.getPatronId()).isPresent()) {
            throw new IllegalArgumentException("Patron with ID " + patron.getPatronId() + " already exists.");
        }
        patronDAO.save(patron);
    }

    // Updates an existing patron's information
    public void updatePatron(Patron patron) {
        validatePatron(patron);
        if (!patronDAO.findById(patron.getPatronId()).isPresent()) {
            throw new IllegalArgumentException("Patron with ID " + patron.getPatronId() + " does not exist.");
        }
        patronDAO.update(patron);
    }

    // Retrieves a patron by their ID
    public Patron getPatronById(String patronId) {
        Patron patron = patronDAO.findById(patronId).orElseThrow(() -> 
            new IllegalArgumentException("Patron with ID " + patronId + " not found.")
        );
        return patron;
    }

    // Retrieves all patrons
    public List<Patron> getAllPatrons() {
        return patronDAO.findAll();
    }

    private void validatePatron(Patron patron) {
        if (patron == null) {
            throw new IllegalArgumentException("Patron cannot be null.");
        }
    }
}
