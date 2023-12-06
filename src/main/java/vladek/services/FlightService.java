package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.model.Flight;
import vladek.model.repositories.FlightRepository;
import vladek.services.interfaces.IFlightService;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService implements IFlightService {
    @Autowired
    private FlightRepository repository;

    @Override
    public Flight create(int num, String from, String to, Date departure, Date arriving) {
        Flight flight = new Flight();
        flight.setNum(num);
        flight.setFrom(from);
        flight.setTo(to);
        flight.setDepartureDate(departure);
        flight.setArrivingDate(arriving);
        repository.save(flight);
        return flight;
    }

    @Override
    public Flight update(UUID id, int num, String from, String to, Date departure, Date arriving) throws NoSuchObjectException {
        Flight flight = repository.findById(id).orElse(null);

        if (flight == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        flight.setNum(num);
        flight.setFrom(from);
        flight.setTo(to);
        flight.setDepartureDate(departure);
        flight.setArrivingDate(arriving);
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
