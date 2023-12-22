package vladek.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladek.models.Vehicle;
import vladek.services.VehicleService;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    private final Logger logger = Logger.getLogger(VehicleController.class.getName());

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle v) {
        Vehicle vehicle = vehicleService.create(v.getSits(), v.getType());
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicle> update(@RequestBody Vehicle v) {
        try {
            Vehicle vehicle = vehicleService.update(v.getId(), v.getSits(), v.getType());
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        vehicleService.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Vehicle> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        try {
            Vehicle vehicle = vehicleService.get(uuid);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Vehicle>> getAll() {
        List<Vehicle> vehicles = vehicleService.getAll();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
}

// curl -X POST http://localhost:8080/vehicle/create -H "Content-Type: application/json" -d "{\"sits\":100,\"type\":\"Boeing 747\"}"
// curl -X PUT --data "sits=1&type=A" http://localhost:8080/vehicle/update/cd6f609e-c1e1-434f-b171-1865a85b98d6
// curl -X DELETE http://localhost:8080/vehicle/delete/cd6f609e-c1e1-434f-b171-1865a85b98d6
// curl -X GET http://localhost:8080/vehicle/get/278450ae-6b4d-4604-98eb-3b3b9900363a
// curl -X GET http://localhost:8080/vehicle/get