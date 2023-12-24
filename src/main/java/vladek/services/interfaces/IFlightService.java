package vladek.services.interfaces;

import vladek.models.Airport;
import vladek.models.Flight;

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
    List<Flight> getFlightsWhenDate(UUID from, UUID to, Date when);
}
