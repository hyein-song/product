package com.ssg.product.payload.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.PromotionEntity;
import com.ssg.product.entity.PromotionItemEntity;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PromotionItemResponse {

    private ItemEntity item;
    private PromotionEntity promotion;

    private Long originalAmount; // 원래 가경
    private Long discountAmount; // 할인 가격

    public PromotionItemResponse convertEntityToResponse(PromotionItemEntity promotionItem){
        return PromotionItemResponse.builder()
                .item(promotionItem.getItem())
                .promotion(promotionItem.getPromotion())
                .originalAmount(promotionItem.getItem().getItemPrice())
                .discountAmount(promotionItem.getFinalDiscountedAmount())
                .build();
    }

}
