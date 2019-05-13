package com.example.demo.controllers;

import java.util.List;

import com.example.demo.entities.Company;
import com.example.demo.entities.User;
import com.example.demo.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UsersController{
  
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @ApiOperation(value = "Return all existing users")
    @GetMapping("api/users")
    public ResponseEntity<List<User>> returnUsers(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(usersService.returnAllUsers());
    }

    @ApiOperation(value = "Return user by it's ID")
    @GetMapping("api/users/findById/{id}")
    public ResponseEntity<User> returnUser(@PathVariable Long id){
        User user = usersService.returnUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(user);
    } 

    @ApiOperation(value = "Return user by it's UserName")
    @GetMapping("api/users/findByUserName/{userName}")
    public ResponseEntity<User> returnUserByUserName(@PathVariable String userName){
        User user = usersService.returnUserByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK)
            .body(user);
    }

    @ApiResponse(code = 200, message = "OK", response = Long.class)
    @ApiOperation(value = "Add a new user")
    @PostMapping("api/user")
    public ResponseEntity<Long> createNewUser(@RequestBody User newUser){
        return ResponseEntity.status(HttpStatus.OK)
            .body(usersService.addNewUser(newUser));
    }

    @ApiOperation(value = "Add a Company to an existing user")
    @ApiResponses(value = {
                @ApiResponse(code = 204, message = "If valid company and valid user"),
                @ApiResponse(code = 400, message = "If invalid company provided"),
                @ApiResponse(code = 404, message = "If provided user name not exists")
            })
    @PutMapping("api/user/{userName}/company")
    public ResponseEntity<?> addCompanyToUser(@PathVariable String userName, 
                                            @RequestBody Company company){
        usersService.addCompanyToUser(userName, company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Delete existing user")
    @DeleteMapping("api/user/{userName}")
    public ResponseEntity removeUser(@PathVariable String userName){
        usersService.removeUserByUseName(userName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}