package vladek.controllers;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vladek.models.User;
import vladek.services.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> registration(@RequestBody User user) {
        try {
            User u = userService.create(user);
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping
//    public String addUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        try {
//            User user = userService.create(userForm);
//        } catch (EntityExistsException e) {
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registration";
//        }
//
//        return "redirect:/";
//    }
}
