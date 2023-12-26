package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.Airport;
import vladek.models.Flight;
import vladek.services.repositories.FlightRepository;
import vladek.services.interfaces.IFlightService;

import java.rmi.NoSuchObjectException;
import java.util.*;

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

    /**
     * Возвращает список рейсов из аэропорта в другой за сутки
     * @param from откуда
     * @param to куда
     * @param when когда
     * @return Список рейсов
     */
    @Override
    public List<Flight> getFlightsWhenDate(UUID from, UUID to, Date when) {
        List<Flight> flights = getAll();
        Date start = when;
        Date end = addDay(start);
        flights.removeIf(flight ->
                !flight.getFrom().getId().equals(from)
                || !flight.getTo().getId().equals(to)
                || flight.getDepartureDate().before(start) && flight.getDepartureDate().after(end)
        );
        return flights;
    }

    private Date addDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public boolean validateBeforeSaving(Flight flight) {
        if (flight.getFrom() == null) {
            return false;
        }

        return true;
    }
}
