package com.ssg.product.payload.request;

import com.ssg.product.entity.ItemEntity;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
public class PromotionInsertRequest {

    @NotBlank
    private String promotionNm;

    private Long discountAmount;

    private Double discountRate;

    @NotBlank
    private LocalDate promotionStartDate;
    @NotBlank
    private LocalDate promotionEndDate;

    private List<Long> itemIdList = new ArrayList<>();

}
