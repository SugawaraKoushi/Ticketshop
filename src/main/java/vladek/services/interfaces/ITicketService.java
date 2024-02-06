package vladek.services.interfaces;

import vladek.models.Ticket;
import vladek.models.User;

import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ITicketService {
    Ticket create(Ticket ticket) throws Exception;
    Ticket update(UUID id, String name, Date departureDate, Date purchaseDate, Date bookingDate, int price) throws NoSuchObjectException;
    void delete(UUID id);
    Ticket get(UUID id) throws NoSuchObjectException;
    List<Ticket> getAll();
}
