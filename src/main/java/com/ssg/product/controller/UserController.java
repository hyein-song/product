package com.ssg.product.controller;


import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<String> userInsert(@RequestBody UserInsertRequest userInsertRequest){
        return userService.userInsert(userInsertRequest);
    }
}
