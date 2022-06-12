package com.ssg.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.product.entity.value.UserType;
import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void userInsert() throws Exception{
        //given
        ResponseEntity<String> response = new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        UserInsertRequest userInsertRequest = createUserInsertRequest();

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        //mocking
        doReturn(response).when(userService).userInsert(any(UserInsertRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInsertRequest))

        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    void userDelete() throws Exception {
        //given
        ResponseEntity<String> response = new ResponseEntity<>("회원 탈퇴 성공", HttpStatus.OK);
        Long fakeId = 1L;

        //mocking
        doReturn(response).when(userService).userDelete(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/user/{userId}",fakeId)

        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    private UserInsertRequest createUserInsertRequest(){
        return UserInsertRequest.builder()
                .userName("홍길동")
                .userType(UserType.NORMAL)
                .build();
    }
}