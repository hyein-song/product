package com.ssg.product.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name="PROMOTIONITEM")
public class PromotionItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PromotionItemId")
    private Long id;

    @ManyToOne
    @JoinColumn(name="promotion_id")
    @JsonIgnore
    private PromotionEntity promotion;

    @ManyToOne
    @JoinColumn(name="item_id")
    @JsonIgnore
    private ItemEntity item;

    private Long finalDiscountedAmount;

    public PromotionItemEntity getDiscountAmount(){
        Long originalAmount = this.getItem().getItemPrice();
        Long discountAmount = this.getItem().getItemPrice();

        Long discountPromotionAmount = this.getPromotion().getDiscountAmount();
        Double discountPromotionRate = this.getPromotion().getDiscountRate();

        if (discountPromotionAmount != null && discountPromotionAmount < originalAmount){ // 할인률이 있고, 원래 가격보다 클때
            discountAmount = originalAmount-discountPromotionAmount;

        } else if (discountPromotionRate != null){
            Double tmp = Math.ceil(originalAmount * (1.0-discountPromotionRate));
            discountAmount = tmp.longValue();
        }

        this.finalDiscountedAmount = discountAmount;

        return this;
    }

}
