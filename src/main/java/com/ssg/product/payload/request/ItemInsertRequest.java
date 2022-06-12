package com.ssg.product.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.product.entity.value.ItemType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class ItemInsertRequest {

    @NotBlank
    private String itemName;

    @NotNull
    private ItemType itemType;

    @NotNull
    @Min(0)
    private Long itemPrice;


    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayStartDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayEndDate;

}
