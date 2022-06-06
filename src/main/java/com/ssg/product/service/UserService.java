package com.ssg.product.service;

import com.ssg.product.entity.UserEntity;
import com.ssg.product.payload.dto.ResponseDTO;
import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<String> userInsert(UserInsertRequest userInsertRequest){
        if (userRepository.findByUserNameAndUserType(userInsertRequest.getUserName(), userInsertRequest.getUserType()).isPresent()){
            throw new IllegalStateException("해당 이름과 타입의 유저가 존재합니다.");
        }

        
        UserEntity user = UserEntity.builder()
                .userName(userInsertRequest.getUserName())
                .userType(userInsertRequest.getUserType())
                .userStat("정상")
                .build();

        userRepository.save(user);

        return new ResponseEntity<String>("회원가입 성공", HttpStatus.OK);

    }
}
