package com.ssg.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Long id;

    private String userName;

    private String userType; // enum으로 변경

    private String userState; // enum으로 변경

}
