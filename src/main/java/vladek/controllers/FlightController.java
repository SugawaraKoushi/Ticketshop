package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.models.Category;
import vladek.models.Flight;
import vladek.services.FlightService;

import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> update(@RequestBody Flight f) {
        try {
            Flight flight = flightService.update(f);
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

    @GetMapping("/getWhenDate")
    public ResponseEntity<List<Flight>> getWhenDate(@RequestParam UUID from, @RequestParam UUID to, @RequestParam Date when) {
        List<Flight> flights = flightService.getFlightsWhenDate(from, to, when);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/get-categories")
    public ResponseEntity<Map<UUID, List<Category>>> getFlightsCategories(@RequestParam List<Flight> flights) {
        Map<UUID, List<Category>> flightsWithCategories = flightService.getFlightsCategories(flights);
        return new ResponseEntity<>(flightsWithCategories, HttpStatus.OK);
    }
}