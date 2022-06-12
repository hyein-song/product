package com.ssg.product.payload.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PromotionItemInsertRequest {

    @NotNull
    private Long promotionId;

    @NotNull
    private Long itemId;
}
