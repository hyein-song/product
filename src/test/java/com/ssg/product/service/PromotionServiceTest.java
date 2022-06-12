package com.ssg.product.service;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.PromotionEntity;
import com.ssg.product.entity.PromotionItemEntity;
import com.ssg.product.entity.value.ItemType;
import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.payload.request.PromotionItemInsertRequest;
import com.ssg.product.payload.response.PromotionItemResponse;
import com.ssg.product.repository.ItemRepository;
import com.ssg.product.repository.PromotionItemRepository;
import com.ssg.product.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private PromotionItemRepository promotionItemRepository;

    @Test
    void promotionInsert() {
        //given
        PromotionInsertRequest promotionInsertRequest = createpromotionInsertRequest();
        PromotionEntity promotion = createPromotionEntity(promotionInsertRequest);

        //mocking
        doReturn(promotion).when(promotionRepository).save(any(PromotionEntity.class));

        //when
        ResponseEntity<String> result = promotionService.promotionInsert(promotionInsertRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("프로모션 정보 저장 완료");
    }

    @Test
    void promotionDelete() {
        //given
        PromotionEntity promotion = PromotionEntity.builder().build();

        //mocking
        doReturn(Optional.of(promotion)).when(promotionRepository).findById(1L);

        //when
        ResponseEntity<String> result = promotionService.promotionDelete(1L);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("프로모션 정보 삭제 완료");

    }

    @Test
    void promotionItemInsert() {
        //given
        ItemEntity item = ItemEntity.builder().build();
        PromotionEntity promotion = PromotionEntity.builder().build();
        PromotionItemEntity promotionItem = PromotionItemEntity.builder().build();

        PromotionItemInsertRequest promotionItemInsertRequest = PromotionItemInsertRequest.builder()
                .promotionId(1L)
                .itemId(1L)
                .build();

        //mocking
        doReturn(Optional.of(promotion)).when(promotionRepository).findById(1L);
        doReturn(Optional.of(item)).when(itemRepository).findById(1L);
        doReturn(promotionItem).when(promotionItemRepository).save(any(PromotionItemEntity.class));

        //then
        ResponseEntity<String> result = promotionService.promotionItemInsert(promotionItemInsertRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("프로모션상품 등록 완료");

    }

    @Test
    void promotionItemInformation() {
        //given
        ItemEntity item = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        PromotionEntity promotion1 = PromotionEntity.builder()
                .promotionNm("promotion1")
                .discountAmount(1000L)
                .promotionStartDate(LocalDate.parse("2022-01-01"))
                .promotionEndDate(LocalDate.parse("2023-01-01"))
                .build();

        // 기간 지난 promotion
        PromotionEntity promotion2 = PromotionEntity.builder()
                .promotionNm("promotion1")
                .discountAmount(2000L)
                .promotionStartDate(LocalDate.parse("2021-01-01"))
                .promotionEndDate(LocalDate.parse("2022-01-01"))
                .build();

        PromotionItemEntity promotionItem1 = PromotionItemEntity.builder()
                .promotion(promotion1)
                .item(item)
                .build();

        PromotionItemEntity promotionItem2 = PromotionItemEntity.builder()
                .promotion(promotion2)
                .item(item)
                .build();

        List<PromotionItemEntity> promotionEntities = new ArrayList<>();
        promotionEntities.add(promotionItem1);
        promotionEntities.add(promotionItem2);

        LocalDate nowDate = LocalDate.now();

        //mocking
        doReturn(Optional.of(item)).when(itemRepository).findById(1L);
        doReturn(promotionEntities).when(promotionItemRepository).findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(item,nowDate,nowDate);

        //when
        PromotionItemResponse result = promotionService.promotionItemInformation(1L);

        //then
        assertThat(result.getPromotion()).isEqualTo(promotion2);
        assertThat(result.getItem()).isEqualTo(item);

    }

    private PromotionInsertRequest createpromotionInsertRequest() {
        return PromotionInsertRequest.builder()
                .promotionNm("promotion1")
                .discountAmount(1000L)
                .promotionStartDate(LocalDate.parse("2022-01-01"))
                .promotionEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }

    private PromotionEntity createPromotionEntity(PromotionInsertRequest promotionInsertRequest) {
        return PromotionEntity.builder()
                .promotionNm(promotionInsertRequest.getPromotionNm())
                .discountAmount(promotionInsertRequest.getDiscountAmount())
                .discountRate(promotionInsertRequest.getDiscountRate())
                .promotionStartDate(promotionInsertRequest.getPromotionStartDate())
                .promotionEndDate(promotionInsertRequest.getPromotionEndDate())
                .build();
    }

}