package com.ssg.product.service;

import com.ssg.product.entity.UserEntity;
import com.ssg.product.entity.value.UserType;
import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void userInsert() {
        //given
        UserInsertRequest userInsertRequest = createUserInsertRequest();
        UserEntity user = createuser(userInsertRequest);

        //mocking
        doReturn(Optional.ofNullable(null)).when(userRepository).findByUserNameAndUserType(user.getUserName(), user.getUserType());
        doReturn(user).when(userRepository).save(any(UserEntity.class));

        //when
        ResponseEntity<String> result = userService.userInsert(userInsertRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("회원가입 성공");

    }

    @Test
    void userDelete() {
        //given
        UserInsertRequest userInsertRequest = createUserInsertRequest();
        UserEntity user = createuser(userInsertRequest);

        //mocking
        doReturn(Optional.of(user)).when(userRepository).findById(1L);

        //when
        ResponseEntity<String> result = userService.userDelete(1L);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("회원 탈퇴 성공");
    }

    private UserEntity createuser(UserInsertRequest userInsertRequest) {
        return UserEntity.builder()
                .userName(userInsertRequest.getUserName())
                .userType(userInsertRequest.getUserType())
                .userStat("정상")
                .build();

    }

    private UserInsertRequest createUserInsertRequest() {
        return UserInsertRequest.builder()
                .userName("홍길동")
                .userType(UserType.NORMAL)
                .build();
    }

}