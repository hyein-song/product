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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ItemId")
    private Long id;

    @Column(name="ItemName")
    private String itemName;

    @Column(name="ItemType")
    private String itemType;

    @Column(name="ItemPrice")
    private Long itemPrice;

    @Column(name="ItemDisplayStartDate")
    private LocalDate itemDisplayStartDate;
    @Column(name="ItemDisplayEndDate")
    private LocalDate itemDisplayEndDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="promotion_id")
//    private PromotionEntity promotionEntity;
}
