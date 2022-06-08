package com.ssg.product.payload.request;

import com.ssg.product.entity.value.UserType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserInsertRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private UserType userType;
}
