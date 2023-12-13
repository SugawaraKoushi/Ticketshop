package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.model.Flight;
import vladek.services.FlightService;

import java.rmi.NoSuchObjectException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService flightService;
    private final Logger logger = Logger.getLogger(FlightController.class.getName());

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> create(@RequestBody Flight f) {
        Flight flight = flightService.create(f);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> update(@RequestBody Flight f) {
        try {
            Flight flight = flightService.update(f.getId(), f.getNum(), f.getFrom(), f.getTo(), f.getDepartureDate(), f.getArrivingDate(), f.getVehicle());
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        flightService.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Flight> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        try {
            Flight flight = flightService.get(uuid);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Flight>> getAll() {
        List<Flight> flights = flightService.getAll();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}