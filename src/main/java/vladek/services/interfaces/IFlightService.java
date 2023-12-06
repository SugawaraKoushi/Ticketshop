package vladek.services.interfaces;

import vladek.model.Flight;

import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IFlightService {
    Flight create(int num, String from, String to, Date departure, Date arriving);
    Flight update(UUID id, int num, String from, String to, Date departure, Date arriving) throws NoSuchObjectException;
    void delete(UUID id);
    Flight get(UUID id) throws NoSuchObjectException;

    List<Flight> getAll();
}
