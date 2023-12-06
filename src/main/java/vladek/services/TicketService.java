package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.model.Ticket;
import vladek.model.repositories.TicketRepository;
import vladek.services.interfaces.ITicketService;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService implements ITicketService {
    @Autowired
    private TicketRepository repository;

    @Override
    public Ticket create(String name, Date departureDate, Date purchaseDate, Date bookingDate, int price) {
        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setDepartureDate(departureDate);
        ticket.setPurchaseDate(purchaseDate);
        ticket.setBookingDate(bookingDate);
        ticket.setPrice(price);
        repository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket update(UUID id, String name, Date departureDate, Date purchaseDate, Date bookingDate, int price) throws NoSuchObjectException {
        Ticket ticket = repository.findById(id).orElse(null);

        if (ticket == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        ticket.setName(name);
        ticket.setDepartureDate(departureDate);
        ticket.setPurchaseDate(purchaseDate);
        ticket.setBookingDate(bookingDate);
        ticket.setPrice(price);
        repository.save(ticket);
        return ticket;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Ticket get(UUID id) throws NoSuchObjectException {
        Ticket ticket = repository.findById(id).orElse(null);

        if (ticket == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        repository.findAll().forEach(tickets::add);
        return tickets;
    }
}
