package com.ssg.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PROMOTION")
@DynamicInsert
@DynamicUpdate
public class PromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PromotionId")
    private Long id;

    @Column(name="PromotionNm")
    private String promotionNm;

    @Column(name="DiscountAmount")
    private Long discountAmount;

    @Column(name="DiscountRate")
    private Double discountRate;

    @Column(name="PromotionStartDate")
    private LocalDate itemDisplayStartDate;
    @Column(name="PromotionEndDate")
    private LocalDate itemDisplayEndDate;

    @OneToMany(mappedBy = "promotionEntity")
    private List<ItemEntity> itemEntities = new ArrayList<>();
}
