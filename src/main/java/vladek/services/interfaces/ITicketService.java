package vladek.services.interfaces;

import vladek.model.Ticket;

import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.UUID;

public interface ITicketService {
    Ticket create(String name, Date departureDate, Date purchaseDate, Date bookingDate, int price);
    Ticket update(UUID id, String name, Date departureDate, Date purchaseDate, Date bookingDate, int price) throws NoSuchObjectException;
    void delete(UUID id);
    Ticket get(UUID id) throws NoSuchObjectException;
}
