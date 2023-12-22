package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.models.City;
import vladek.services.CityService;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;
    private final Logger logger = Logger.getLogger(CityController.class.getName());

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<City> create(@RequestBody City city) {
        City c = cityService.create(city);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<City> update(@RequestBody City city) {
        try {
            City c = cityService.update(city);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        cityService.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<City> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        try {
            City city = cityService.get(uuid);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<City>> getAll() {
        List<City> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
