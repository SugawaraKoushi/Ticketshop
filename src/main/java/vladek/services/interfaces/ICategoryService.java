package vladek.services.interfaces;

import vladek.model.Category;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    Category create(String type, int sits);
    Category update(UUID id, String type, int sits) throws NoSuchObjectException;
    void delete(UUID id);
    Category get(UUID id) throws NoSuchObjectException;

    List<Category> getAll();
}
