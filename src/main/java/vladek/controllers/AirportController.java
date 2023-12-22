package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.models.Airport;
import vladek.services.AirportService;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/airport")
public class AirportController {
    private final AirportService airportService;
    private final Logger logger = Logger.getLogger(AirportController.class.getName());

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Airport> create(@RequestBody Airport airport) {
        Airport c = airportService.create(airport);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Airport> update(@RequestBody Airport airport) {
        try {
            Airport c = airportService.update(airport);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        airportService.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Airport> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        try {
            Airport airport = airportService.get(uuid);
            return new ResponseEntity<>(airport, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Airport>> getAll() {
        List<Airport> airports = airportService.getAll();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }
}
