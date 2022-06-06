package com.ssg.product.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
public class ItemInsertRequest {

    @NotBlank
    private String itemName;

    @NotBlank
    private String itemType;

    @NotBlank
    private Long itemPrice;

    @NotBlank
    private LocalDate itemDisplayStartDate;
    @NotBlank
    private LocalDate itemDisplayEndDate;

}
