package com.ssg.product.payload.request;

import com.ssg.product.entity.value.UserType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UserInsertRequest {

    @NotBlank
    private String userName;

    @NotNull
    private UserType userType;
}
