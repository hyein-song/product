package com.ssg.product.repository;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.PromotionEntity;
import com.ssg.product.entity.PromotionItemEntity;
import com.ssg.product.entity.value.ItemType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PromotionItemRepositoryTest {

    @Autowired
    private PromotionItemRepository promotionItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    void savePromotionItemTest(){
        //given
        PromotionEntity promotion = PromotionEntity.builder().build();
        ItemEntity item = ItemEntity.builder().build();
        PromotionItemEntity promotionItem = PromotionItemEntity.builder()
                .promotion(promotion)
                .item(item)
                .build();

        //when
        PromotionItemEntity result = promotionItemRepository.save(promotionItem);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPromotion()).isEqualTo(promotion);
        assertThat(result.getItem()).isEqualTo(item);

    }

    @Test
    void findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual() {

        //given
        final ItemEntity item1 = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        final PromotionEntity promotion1 = PromotionEntity.builder()
                .promotionNm("promotion1")
                .discountAmount(1000L)
                .promotionStartDate(LocalDate.parse("2022-01-01"))
                .promotionEndDate(LocalDate.parse("2023-01-01"))
                .build();

        // 기간 지난 promotion
        final PromotionEntity promotion2 = PromotionEntity.builder()
                .promotionNm("promotion1")
                .discountAmount(2000L)
                .promotionStartDate(LocalDate.parse("2021-01-01"))
                .promotionEndDate(LocalDate.parse("2022-01-01"))
                .build();

        PromotionItemEntity promotionItem1 = PromotionItemEntity.builder()
                .promotion(promotion1)
                .item(item1)
                .build();

        PromotionItemEntity promotionItem2 = PromotionItemEntity.builder()
                .promotion(promotion2)
                .item(item1)
                .build();

        LocalDate nowDate = LocalDate.now();

        itemRepository.save(item1);
        promotionRepository.save(promotion1);
        promotionRepository.save(promotion2);
        PromotionItemEntity savedEntity1 = promotionItemRepository.save(promotionItem1);
        PromotionItemEntity savedEntity2 = promotionItemRepository.save(promotionItem2);
        Optional<PromotionItemEntity> promotionItemEntity1  = promotionItemRepository.findById(savedEntity1.getId());
        Optional<PromotionItemEntity> promotionItemEntity2  = promotionItemRepository.findById(savedEntity2.getId());
        assertThat(promotionItemEntity1).isPresent();
        assertThat(promotionItemEntity2).isPresent();

        //when
        List<PromotionItemEntity> result = promotionItemRepository.findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(item1,nowDate,nowDate);

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }


}