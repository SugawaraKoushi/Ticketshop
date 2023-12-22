package vladek.services.interfaces;

import vladek.models.Airport;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface IAirportService {
    Airport create(Airport airport);
    Airport update(Airport airport) throws NoSuchObjectException;
    void delete(UUID id);
    Airport get(UUID id) throws NoSuchObjectException;
    List<Airport> getAll();
}
