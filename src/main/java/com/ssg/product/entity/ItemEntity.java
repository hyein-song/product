package com.ssg.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ITEM")
@DynamicInsert
@DynamicUpdate
public class ItemEntity {

    @Id
    private Long id;

    private String itemName;

    private String itemType;

    private Long itemPrice;

    private LocalDate itemDisplayStartDate;
    private LocalDate itemDisplayEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="promotion_id")
    private PromotionEntity promotionEntity;
}
