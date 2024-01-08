package vladek.services;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vladek.models.Role;
import vladek.models.User;
import vladek.services.interfaces.IUserService;
import vladek.services.repositories.RoleRepository;
import vladek.services.repositories.UserRepository;

import java.rmi.NoSuchObjectException;
import java.util.UUID;

public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User create(User user) throws EntityExistsException {
        User u = userRepository.findByUsername(user.getUsername());

        if (u != null) {
            throw new EntityExistsException("User with username already exists");
        }
        user.setRole(new Role(UUID.randomUUID(), "ROLE_USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
}
