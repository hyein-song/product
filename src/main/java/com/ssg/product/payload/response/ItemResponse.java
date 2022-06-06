package com.ssg.product.payload.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssg.product.entity.ItemEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemResponse {

    private String itemName;

    private String itemType;

    private Long itemPrice;

    private LocalDate itemDisplayStartDate;
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
