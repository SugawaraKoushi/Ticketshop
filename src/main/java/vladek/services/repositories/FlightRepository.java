package vladek.services.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vladek.models.Flight;

import java.util.UUID;

@Repository
public interface FlightRepository extends CrudRepository<Flight, UUID> {
}
