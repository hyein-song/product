package com.ssg.product.service;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.PromotionEntity;
import com.ssg.product.entity.PromotionItemEntity;
import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.payload.request.PromotionItemInsertRequest;
import com.ssg.product.payload.response.PromotionItemResponse;
import com.ssg.product.repository.ItemRepository;
import com.ssg.product.repository.PromotionItemRepository;
import com.ssg.product.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ItemRepository itemRepository;
    private final PromotionItemRepository promotionItemRepository;

    @Transactional
    public ResponseEntity<String> promotionInsert(PromotionInsertRequest promotionInsertRequest){

        if (promotionInsertRequest.getDiscountAmount() == null && promotionInsertRequest.getDiscountRate()==null){
            throw new IllegalStateException("할인 가격 또는 할인율 중 하나를 입력해야 합니다.");
        }

        PromotionEntity promotionEntity = PromotionEntity.builder()
                .promotionNm(promotionInsertRequest.getPromotionNm())
                .discountAmount(promotionInsertRequest.getDiscountAmount())
                .discountRate(promotionInsertRequest.getDiscountRate())
                .promotionStartDate(promotionInsertRequest.getPromotionStartDate())
                .promotionEndDate(promotionInsertRequest.getPromotionEndDate())
                .build();

        promotionRepository.save(promotionEntity);

        return new ResponseEntity<>("프로모션 정보 저장 완료", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> promotionDelete(Long promotionId){

        PromotionEntity promotionEntity = promotionRepository.findById(promotionId).orElseThrow(()->
                new NullPointerException("해당 id를 가진 프로모션 정보를 찾지 못했습니다."));

        promotionRepository.delete(promotionEntity);

        return new ResponseEntity<>("프로모션 정보 삭제 완료",HttpStatus.OK);
    }

    public ResponseEntity<String> promotionItemInsert(PromotionItemInsertRequest request){

        PromotionEntity promotion = promotionRepository.findById(request.getPromotionId()).orElseThrow(()->
                new NullPointerException("해당 id를 가진 프로모션 정보를 찾지 못했습니다."));
        ItemEntity item = itemRepository.findById(request.getItemId()).orElseThrow(()->
                new NullPointerException("해당 id를 가진 상품 정보를 찾지 못했습니다."));

        PromotionItemEntity promotionItem = PromotionItemEntity.builder()
                .promotion(promotion)
                .item(item)
                .build();
        promotionItemRepository.save(promotionItem);

        return new ResponseEntity<>("프로모션상품 등록 완료",HttpStatus.OK);
    }

    public PromotionItemResponse promotionItemInformation(Long itemId){
        ItemEntity item = itemRepository.findById(itemId).orElseThrow(()->
                new NullPointerException("해당 id를 가진 상품 정보를 찾지 못했습니다."));

        LocalDate nowDate = LocalDate.now();

        List<PromotionItemEntity> promotionItemEntities =
                promotionItemRepository.findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(item,nowDate,nowDate);

        Comparator<PromotionItemEntity> comparatorByAmount = Comparator.comparingLong(PromotionItemEntity::getFinalDiscountedAmount);

        PromotionItemEntity promotionItem = promotionItemEntities.stream()
                .map(PromotionItemEntity::getDiscountAmount)
                .peek(s -> System.out.println(s.getFinalDiscountedAmount()))
                .min(comparatorByAmount)
                .orElse(PromotionItemEntity.builder().item(item).build());

        return new PromotionItemResponse().convertEntityToResponse(promotionItem);

    }

}
