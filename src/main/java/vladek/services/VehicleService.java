package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.Vehicle;
import vladek.services.repositories.VehicleRepository;
import vladek.services.interfaces.IVehicleService;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService implements IVehicleService {
    @Autowired
    private VehicleRepository repository;

    @Override
    public Vehicle create(int sits, String type) {
        Vehicle vehicle = new Vehicle();
        vehicle.setSits(sits);
        vehicle.setType(type);
        repository.save(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle update(UUID id, int sits, String type) throws NoSuchObjectException {
        Vehicle vehicle = repository.findById(id).orElse(null);

        if (vehicle == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        vehicle.setSits(sits);
        vehicle.setType(type);
        repository.save(vehicle);
        return vehicle;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Vehicle get(UUID id) throws NoSuchObjectException {
        Vehicle vehicle = repository.findById(id).orElse(null);

        if (vehicle == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }
        return vehicle;
    }

    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        repository.findAll().forEach(vehicles::add);
        return vehicles;
    }
}
