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
    private Long id;

    private String promotionNm;

    private Long discountAmount;

    private Double discountRate;

    private LocalDate itemDisplayStartDate;
    private LocalDate itemDisplayEndDate;

    @OneToMany(mappedBy = "promotionEntity")
    private List<ItemEntity> itemEntities = new ArrayList<>();
}
