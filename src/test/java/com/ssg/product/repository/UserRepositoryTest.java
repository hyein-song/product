package com.ssg.product.repository;

import com.ssg.product.entity.UserEntity;
import com.ssg.product.entity.value.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserTest(){
        //given
        final UserEntity user = createuser();

        //when
        UserEntity result = userRepository.save(user);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserName()).isEqualTo("홍길동");
        assertThat(result.getUserType()).isEqualTo(UserType.NORMAL);
        assertThat(result.getUserStat()).isEqualTo("정상");
    }

    @Test
    void findByUserNameAndUserType() {
        //given
        final UserEntity user1 = createuser();
        final String userName = "홍길동";
        final UserType userType = UserType.NORMAL;

        //when
        UserEntity savedUser = userRepository.save(user1);
        Optional<UserEntity> user = userRepository.findById(savedUser.getId());
        assertThat(user).isPresent();

        Optional<UserEntity> result = userRepository.findByUserNameAndUserType(userName,userType);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isNotNull();
        assertThat(result.get().getUserName()).isEqualTo("홍길동");
        assertThat(result.get().getUserType()).isEqualTo(UserType.NORMAL);
        assertThat(result.get().getUserStat()).isEqualTo("정상");

    }

    @Test
    void findByIdAndUserStat() {
        //given
        final UserEntity user1 = createuser();
        final String userStat = "정상";

        //when
        UserEntity savedUser = userRepository.save(user1);
        Optional<UserEntity> user = userRepository.findById(savedUser.getId());
        assertThat(user).isPresent();

        Optional<UserEntity> result = userRepository.findByIdAndUserStat(savedUser.getId(),userStat);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isNotNull();
        assertThat(result.get().getUserName()).isEqualTo("홍길동");
        assertThat(result.get().getUserType()).isEqualTo(UserType.NORMAL);
        assertThat(result.get().getUserStat()).isEqualTo("정상");
    }

    private UserEntity createuser(){
        return UserEntity.builder()
                .userName("홍길동")
                .userType(UserType.NORMAL)
                .userStat("정상")
                .build();
    }


}