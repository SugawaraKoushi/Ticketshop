package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.model.Flight;
import vladek.services.repositories.FlightRepository;
import vladek.services.interfaces.IFlightService;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService implements IFlightService {
    @Autowired
    private FlightRepository repository;

    @Override
    public Flight create(Flight flight) {
        repository.save(flight);
        return flight;
    }

    @Override
    public Flight update(Flight flight) throws NoSuchObjectException {
        Flight f = repository.findById(flight.getId()).orElse(null);

        if (f == null) {
            throw new NoSuchObjectException("No such object with id " + f.getId());
        }

        repository.save(flight);
        return flight;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Flight get(UUID id) throws NoSuchObjectException {
        Flight flight = repository.findById(id).orElse(null);

        if (flight == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return flight;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> flights = new ArrayList<>();
        repository.findAll().forEach(flights::add);
        return flights;
    }
}
