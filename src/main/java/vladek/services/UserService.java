package vladek.services;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.Role;
import vladek.models.User;
import vladek.services.interfaces.IUserService;
import vladek.services.repositories.RoleRepository;
import vladek.services.repositories.UserRepository;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User create(User user) throws EntityExistsException {
        User u = userRepository.findByUsername(user.getUsername());

        if (u != null) {
            throw new EntityExistsException("User with username already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(User user) throws NoSuchObjectException {
        User u = userRepository.findById(user.getId()).orElse(null);

        if (u == null) {
            throw new NoSuchObjectException("No such object with id " + user.getId());
        }

        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User get(UUID id) throws NoSuchObjectException {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User login(String username, String password) throws IllegalArgumentException {
        User user = userRepository.findByUsername(username);

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неправильно введен логин или пароль");
        }

        return user;
    }
}
