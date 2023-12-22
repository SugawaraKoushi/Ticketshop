package vladek.services.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vladek.models.Airport;

import java.util.UUID;

@Repository
public interface AirportRepository extends CrudRepository<Airport, UUID> {
}
