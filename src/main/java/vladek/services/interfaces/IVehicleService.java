package vladek.services.interfaces;

import vladek.model.Vehicle;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface IVehicleService {
    Vehicle create(int sits, String type);
    Vehicle update(UUID id, int sits, String type) throws NoSuchObjectException;
    void delete(UUID id);
    Vehicle get(UUID id) throws NoSuchObjectException;

    List<Vehicle> getAll();
}
