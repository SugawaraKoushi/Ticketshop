package vladek.services.repositories;

import org.springframework.data.repository.CrudRepository;
import vladek.models.Role;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
}
