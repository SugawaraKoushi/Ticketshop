package vladek.services.interfaces;

import vladek.models.City;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface ICityService {
    City create(City city);
    City update(City city) throws NoSuchObjectException;
    void delete(UUID id);
    City get(UUID id) throws NoSuchObjectException;
    List<City> getAll();
}
