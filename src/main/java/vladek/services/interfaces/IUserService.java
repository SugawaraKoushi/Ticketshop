package vladek.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import vladek.models.User;

import java.rmi.NoSuchObjectException;
import java.util.UUID;

public interface IUserService extends UserDetailsService {
    User create(User user);

    User update(User user) throws NoSuchObjectException;

    void delete(UUID id);

    User get(UUID id) throws NoSuchObjectException;
}
