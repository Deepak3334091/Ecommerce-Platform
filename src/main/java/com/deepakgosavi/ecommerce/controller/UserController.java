package com.deepakgosavi.ecommerce.controller;

import com.deepakgosavi.ecommerce.entity.User;
import com.deepakgosavi.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

 private final UserService userService;

 public UserController(UserService userService){
     this.userService=userService;
 }

    @GetMapping("")
    public List<User> getAllUsers() {
        return this.userService.getAllUser();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));

    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return this.userService.updateUser(id, user);
    }

}
