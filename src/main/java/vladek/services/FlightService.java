package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.DTO.FlightWithCategories;
import vladek.models.Category;
import vladek.models.Flight;
import vladek.models.Ticket;
import vladek.models.Vehicle;
import vladek.services.repositories.FlightRepository;
import vladek.services.interfaces.IFlightService;

import java.rmi.NoSuchObjectException;
import java.util.*;

@Service
public class FlightService implements IFlightService {
    @Autowired
    private FlightRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TicketService ticketService;

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
                flight.getDepartureDate().before(start) || flight.getDepartureDate().after(end)
                || !flight.getFrom().getId().equals(from) || !flight.getTo().getId().equals(to)
                || getFlightFreeSeatsCount(flight) < 0
        );

        return flights;
    }

    @Override
    public List<FlightWithCategories> getFlightsCategories(List<Flight> flights) {
        List<FlightWithCategories> result = new ArrayList<>();

        for (Flight flight : flights) {
            Vehicle vehicle = flight.getVehicle();
            List<Category> categories = categoryService.getByVehicle(vehicle);
            FlightWithCategories fwc = new FlightWithCategories();
            fwc.setFlightId(flight.getId());
            fwc.setCategories(categories);
            result.add(fwc);
        }

        return result;
    }

    private Date addDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private int getFlightFreeSeatsCount(Flight flight) {
        List<Ticket> tickets = ticketService.getAll();
        tickets.removeIf(t -> t.getFlight().equals(flight));
        int takenSeatsCount = tickets.size();
        int seatsCount = flight.getVehicle().getSits();
        return seatsCount - takenSeatsCount;
    }
}
