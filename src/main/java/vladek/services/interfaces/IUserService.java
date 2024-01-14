package vladek.services.interfaces;

import vladek.models.User;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;

public interface IUserService {
    User create(User user);

    User update(User user) throws NoSuchObjectException;

    void delete(UUID id);

    User get(UUID id) throws NoSuchObjectException;

    List<User> getAll();

    User login(String username, String password);
}
