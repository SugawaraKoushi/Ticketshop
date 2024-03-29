package vladek.services.interfaces;

import vladek.models.Category;
import vladek.models.Vehicle;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    Category create(Category category) throws IllegalArgumentException;
    Category update(Category category) throws NoSuchObjectException;
    void delete(UUID id);
    Category get(UUID id) throws NoSuchObjectException;
    List<Category> getAll();
    List<Category> getByVehicle(Vehicle vehicle);
}
