package com.vunh.controller;

import com.vunh.dto.requests.UserRequestDTO;
import com.vunh.dto.response.ResponseData;
import com.vunh.dto.response.ResponseError;
import com.vunh.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @PostMapping
    public ResponseData<?> addUser(@Valid @RequestBody UserRequestDTO user) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "User added");
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteUser(@PathVariable @Min(1) int id) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted");
    }

    @GetMapping("{id}")
    public ResponseData<?> getUser(@PathVariable int id) {
//        return new ResponseData<>(HttpStatus.OK.value(), "User 1");
        throw new ResourceNotFoundException("Can't find this id");
    }
}
