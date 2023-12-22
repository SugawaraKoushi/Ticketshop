package vladek.services.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vladek.models.City;

import java.util.UUID;

@Repository
public interface CityRepository extends CrudRepository<City, UUID> {
}
