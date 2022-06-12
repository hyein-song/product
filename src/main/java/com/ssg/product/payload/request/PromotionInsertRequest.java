package com.ssg.product.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Builder
public class PromotionInsertRequest {

    @NotBlank
    private String promotionNm;

    private Long discountAmount;

    private Double discountRate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate promotionStartDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate promotionEndDate;

}
