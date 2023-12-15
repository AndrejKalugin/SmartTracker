package com.tracker.smarttracker.controller;

import com.tracker.smarttracker.dto.UserDto;
import com.tracker.smarttracker.dto.UserRecordDto;
import com.tracker.smarttracker.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userService.findByUserId(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUserById(@RequestBody @Valid UserRecordDto user) {
        return userService.save(user);
    }

    @PutMapping
    public UserDto updateUserById(@RequestBody @Valid UserRecordDto user) {
        return userService.save(user);
    }
}
