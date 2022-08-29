package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Login;
import com.bertopcu.KitchenWorld.model.User;
import com.bertopcu.KitchenWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    public InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/all")
    public List<User> list() {
        return userService.listAllUser();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public void add(@RequestBody User user) {
        userService.saveUser(user);
        String role = user.getType() == 1 ? "ADMIN" : "USER";
        UserDetails ud = org.springframework.security.core.userdetails.User
                .withUsername(user.getuName())
                .password("{noop}"+user.getPwd())
                .roles(role)
                .build();
        inMemoryUserDetailsManager.createUser(ud);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existUser = userService.getUser(id);
            user.setId(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        userService.deleteUser(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Login credentials) {
        try {
            User user = userService.loginViaEmail(credentials.getIdentifier(), credentials.getPwd());
            if (user == null) {
                user = userService.loginViaUserName(credentials.getIdentifier(), credentials.getPwd());
                if (user == null) {
                    return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}