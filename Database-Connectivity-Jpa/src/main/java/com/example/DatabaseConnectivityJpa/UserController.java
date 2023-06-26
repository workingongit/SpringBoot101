package com.example.DatabaseConnectivityJpa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getUser() throws SQLException {
        return userService.getUsers();
        //TODO : Return all the user in the system
    }

    //Now we cannot have multiple user with same user id. Make id as primary key
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") int userId) throws SQLException {
        //TODO : Return the user with specific userid in the system
        return userService.getUserById(userId);
    }

    //Why didn't we take User object instead of UserCreateRequest
    //If there are 20 parameter in User and front end only wants 3 parameter to be shown on front-end
    //Rest other parameter we have to deduce ourselves . It does not make sense to use it
    @PostMapping("/users")
    //Using @Valid we can apply some validation in UserCreateRequest class
    //We should apply validation on request (UserCreateRequest) not on something which is going to be saved in DB (User)
    //If not provided valid email - ERROR [Field error in object 'userCreateRequest' on field 'email': rejected value [ac123];
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) throws SQLException {
        //TODO : Creating new user with given detail
        try {
            userService.createUser(userCreateRequest);
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //We cannot delete all user at one go therefore requestparam
    //You wont get any error if you delete any id which is not present in db
    @DeleteMapping("/user")
    public User deleteUser(@RequestParam("id") int userId) throws SQLException {
        return userService.deleteUser(userId);
        //TODO : Deleting  user with given id
    }
//
//    @PostMapping("/updateuser")
//    public void updateUser(@RequestParam("id") int userId,@RequestBody UserUpdateRequest userUpdateRequest) throws SQLException {
//        userService.updateUser(userId,userUpdateRequest);
//    }

}
