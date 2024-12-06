package util;

import model.Patron;
import dao.PatronDAO;

import java.util.Optional;

public class AuthenticationUtil {

    private PatronDAO patronDAO;

    public AuthenticationUtil(PatronDAO patronDAO) {
        this.patronDAO = patronDAO;
    }

    /**
     * Authenticates a user by checking the provided credentials.
     *
     * @param patronId The patron's ID.
     * @param password The patron's password.
     * @return True if the credentials are valid, false otherwise.
     */
    public boolean authenticate(String patronId, String password) {
        Optional<Patron> patronOptional = patronDAO.findById(patronId);
        if (patronOptional.isPresent()) {
            Patron patron = patronOptional.get();
            return patron.getPassword().equals(password);  // Check if the password matches
        }
        return false;  // Invalid patron ID or password
    }

    /**
     * Registers a new patron (creates a new patron account).
     *
     * @param patron The patron to be registered.
     */
    public void registerPatron(Patron patron) {
        if (patron == null || patron.getPatronId() == null || patron.getPassword() == null) {
            throw new IllegalArgumentException("Invalid patron data.");
        }
        patronDAO.save(patron); // Save the new patron into the database
    }

    /**
     * Logs out a patron by invalidating their session (for simplicity, not implemented here).
     *
     * @param patron The patron who is logging out.
     */
    public void logout(Patron patron) {
        // In a real application, this could invalidate a session token, etc.
        // For this example, we'll just print a message.
        System.out.println("Patron " + patron.getName() + " logged out successfully.");
    }
}
