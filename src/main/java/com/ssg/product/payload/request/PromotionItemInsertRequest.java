package com.ssg.product.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PromotionItemInsertRequest {

    @NotBlank
    private Long promotionId;

    @NotBlank
    private Long itemId;
}
