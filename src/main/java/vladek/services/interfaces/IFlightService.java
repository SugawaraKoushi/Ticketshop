package vladek.services.interfaces;

import vladek.model.Flight;
import vladek.model.Vehicle;

import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IFlightService {
    Flight create(Flight flight);
    Flight update(Flight flight) throws NoSuchObjectException;
    void delete(UUID id);
    Flight get(UUID id) throws NoSuchObjectException;

    List<Flight> getAll();
}
