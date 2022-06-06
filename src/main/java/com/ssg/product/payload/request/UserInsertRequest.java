package com.ssg.product.payload.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
public class UserInsertRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String userType;
}
