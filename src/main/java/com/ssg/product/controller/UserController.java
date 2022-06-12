package com.ssg.product.controller;


import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> userInsert(@RequestBody @Valid UserInsertRequest userInsertRequest){
        return userService.userInsert(userInsertRequest);
    }

    // 아예 삭제하는 API 필요?
    @PutMapping("/{userId}")
    public ResponseEntity<String> userDelete(@PathVariable Long userId){
        return userService.userDelete(userId);
    }
}
