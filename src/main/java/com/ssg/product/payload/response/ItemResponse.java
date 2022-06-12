package com.ssg.product.payload.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.value.ItemType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemResponse {

    private String itemName;

    private ItemType itemType;

    private Long itemPrice;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayEndDate;

    public ItemResponse convertEntityToResponse(ItemEntity item){
        return ItemResponse.builder()
                .itemName(item.getItemName())
                .itemType(item.getItemType())
                .itemPrice(item.getItemPrice())
                .itemDisplayStartDate(item.getItemDisplayStartDate())
                .itemDisplayEndDate(item.getItemDisplayEndDate())
                .build();
    }
}
