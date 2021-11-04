package learn.events.controllers;


import learn.events.domain.UserService;
import learn.events.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/user/role")
    public List<String> findAllRoles() {
        return service.findAllRoles();
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findByUserId(@PathVariable int id) {
        var user = service.findByUserId(id);
        user.setPassword("");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<Object> create(@RequestBody User user) {

        var result = service.add(user);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/user/update")
    public ResponseEntity<Object> update(
            @RequestBody User user,
            @AuthenticationPrincipal User principal) {

        if (!principal.hasAuthority("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var existing = service.findByUserId(user.getUserId());
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        if (existing.hasAuthority("ADMIN") && existing.getUserId() != principal.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var result = service.update(user);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/user/password")
    public ResponseEntity<Object> changePassword(
            @RequestBody HashMap<String, String> values,
            @AuthenticationPrincipal User principal) {


        principal.setPassword(values.get("password"));

        var result = service.changePassword(principal);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
