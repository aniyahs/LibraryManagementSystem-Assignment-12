package dao;

import model.Patron;

import java.util.List;
import java.util.Optional;

/**
 * PatronDAO defines the interface for accessing and managing patron data.
 */
public interface PatronDAO {

    void save(Patron patron);

    void delete(String patronId);

    void update(Patron patron);

    Optional<Patron> findById(String patronId);

    List<Patron> findAll();
}

