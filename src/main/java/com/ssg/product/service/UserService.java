package com.ssg.product.service;

import com.ssg.product.entity.UserEntity;
import com.ssg.product.payload.request.UserInsertRequest;
import com.ssg.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
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

    public ResponseEntity<String> userDelete(Long user_id){
        UserEntity user = userRepository.findById(user_id).orElseThrow(()-> new NullPointerException("해당 유저가 존재하지 않습니다."));

        user.changeUserStat();
        userRepository.save(user);

        return new ResponseEntity<String>("회원 탈퇴 성공", HttpStatus.OK);
    }
}
