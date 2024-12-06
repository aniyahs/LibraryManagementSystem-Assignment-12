package dao;


import model.Book;
import model.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * InventoryDAO defines the interface for accessing and managing inventory data.
 */
public interface InventoryDAO {

    void updateInventory(Inventory inventory);

    Optional<Inventory> findInventoryByBookId(String bookId);

    void save(Book book);

    void delete(String isbn);

    void update(Book book);

    List<Book> findAll();
}

