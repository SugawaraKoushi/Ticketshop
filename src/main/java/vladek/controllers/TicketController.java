package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.models.Ticket;
import vladek.models.User;
import vladek.services.TicketService;

import java.rmi.NoSuchObjectException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final Logger logger = Logger.getLogger(TicketController.class.getName());

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Ticket ticket) {
        try {
            Ticket t = ticketService.create(ticket);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ticket> update(@PathVariable String id, String name, String departure,String purchase,
                                         String booking, int price) {
        UUID uuid = UUID.fromString(id);
        Date departureDate = Timestamp.valueOf(departure);
        Date purchaseDate = Timestamp.valueOf(purchase);
        Date bookingDate = Timestamp.valueOf(booking);

        try {
            Ticket ticket = ticketService.update(uuid, name, departureDate, purchaseDate, bookingDate, price);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        ticketService.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Ticket> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        try {
            Ticket ticket = ticketService.get(uuid);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Ticket>> getAll() {
        List<Ticket> tickets = ticketService.getAll();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
