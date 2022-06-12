package com.ssg.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssg.product.entity.value.ItemType;
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
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name="ItemPrice")
    private Long itemPrice;

    @Column(name="ItemDisplayStartDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayStartDate;


    @Column(name="ItemDisplayEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate itemDisplayEndDate;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<PromotionItemEntity> promotionEntities= new ArrayList<>();

}
