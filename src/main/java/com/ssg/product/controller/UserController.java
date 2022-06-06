package com.ssg.product.controller;


import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> userInsert(@RequestBody UserInsertRequest userInsertRequest){
        return userService.userInsert(userInsertRequest);
    }

    // 아예 삭제하는 API 필요?
    @PutMapping("delete/{user_id}")
    public ResponseEntity<String> userDelete(@PathVariable Long user_id){
        return userService.userDelete(user_id);
    }
}
