package vladek.controllers;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vladek.models.City;
import vladek.models.User;
import vladek.services.UserService;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private User currentUser;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            User u = userService.create(user);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (EntityExistsException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@RequestBody User user) {
        try {
            User u = userService.update(user);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        userService.delete(uuid);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        try {
            User user = userService.get(uuid);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchObjectException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-current")
    public ResponseEntity<User> getCurrentUser() {
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        try {
            currentUser = userService.login(username, password);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        currentUser = null;
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
