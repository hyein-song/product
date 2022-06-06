package com.ssg.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER")
@DynamicInsert
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UserId")
    private Long id;

    @Column(name="UserName")
    private String userName;

    @Column(name="UserType")
    private String userType;

    @Column(name="UserStat")
    private String userStat;

    public void changeUserStat(){
        this.userStat = "탈퇴";
    }

}
