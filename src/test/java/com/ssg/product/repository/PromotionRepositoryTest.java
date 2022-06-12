package com.ssg.product.repository;

import com.ssg.product.entity.PromotionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository promotionRepository;


    @Test
    void savePromotionTest(){
        //given
        final PromotionEntity promotion1 = promotion();

        //when
        PromotionEntity result = promotionRepository.save(promotion1);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPromotionNm()).isEqualTo("promotion1");
        assertThat(result.getDiscountAmount()).isEqualTo(1000L);
        assertThat(result.getPromotionStartDate()).isEqualTo(LocalDate.parse("2022-01-01"));
        assertThat(result.getPromotionEndDate()).isEqualTo(LocalDate.parse("2023-01-01"));

    }

    @Test
    void findByIdPromotionTest(){
        //given
        final PromotionEntity promotion1 = promotion();

        //when
        PromotionEntity savedPromotion = promotionRepository.save(promotion1);
        Optional<PromotionEntity> result = promotionRepository.findById(savedPromotion.getId());
        assertThat(result).isPresent();

        //then
        assertThat(result.get().getId()).isEqualTo(savedPromotion.getId());
        assertThat(result.get().getPromotionNm()).isEqualTo("promotion1");
        assertThat(result.get().getDiscountAmount()).isEqualTo(1000L);
        assertThat(result.get().getPromotionStartDate()).isEqualTo(LocalDate.parse("2022-01-01"));
        assertThat(result.get().getPromotionEndDate()).isEqualTo(LocalDate.parse("2023-01-01"));

    }

    @Test
    void deletePromotionTest(){
        //given
        final PromotionEntity promotion1 = promotion();

        //when
        PromotionEntity savedPromotion = promotionRepository.save(promotion1);
        Optional<PromotionEntity> promotion = promotionRepository.findById(savedPromotion.getId());
        assertThat(promotion).isPresent();

        promotion.ifPresent(selectedPromotion -> {promotionRepository.delete(selectedPromotion);});
        Optional<PromotionEntity> result = promotionRepository.findById(savedPromotion.getId());

        //then
        assertThat(result).isEmpty();

    }

    private PromotionEntity promotion(){
        return PromotionEntity.builder()
                .promotionNm("promotion1")
                .discountAmount(1000L)
                .promotionStartDate(LocalDate.parse("2022-01-01"))
                .promotionEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }

}