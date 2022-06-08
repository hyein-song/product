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

        return new ResponseEntity<String>("프로모션 정보 저장 완료", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> promotionDelete(Long promotion_id){

        PromotionEntity promotionEntity = promotionRepository.findById(promotion_id).orElseThrow(()->
                new NullPointerException("해당 id를 가진 프로모션 정보를 찾지 못했습니다."));

        promotionRepository.delete(promotionEntity);

        return new ResponseEntity<String>("프로모션 정보 삭제 완료",HttpStatus.OK);
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

    public PromotionItemResponse promotionItemInformation(Long item_id){
        ItemEntity item = itemRepository.findById(item_id).orElseThrow(()->
                new NullPointerException("해당 id를 가진 상품 정보를 찾지 못했습니다."));

        LocalDate nowDate = LocalDate.now();

        //1. 기간
        //2. 프로모션 할인 가격 or 할인률 적용
        // 3. 적용 시 0원 이하이면 프로모션 적용되면 안됨 (가격 적용될때 아이템 가격 이상이면 적용되지 않게)

        List<PromotionItemEntity> promotionItemEntities =
                promotionItemRepository.findByItemAndPromotionStartDateLessThanEqualAndPromotionEndDateGreaterThanEqual(item,nowDate,nowDate);

        // 상품이랑 프로모션을 이을때 할인값을 넣어줘서 그걸 비교?
        // 아니면 기간 안의 프로모션을 상품에 적용시켜서 그것의 최소를 고르기? > 근데 최소값도 리턴해야되는데 모름

        // response에만 저장해서 넘기면 되지않니

        Comparator<PromotionItemEntity> comparatorByAmount = Comparator.comparingLong(PromotionItemEntity::getFinalDiscountedAmount);

        PromotionItemEntity promotionItem = promotionItemEntities.stream()
                .map(PromotionItemEntity::getDiscountAmount)
                .peek(s -> System.out.println(s.getFinalDiscountedAmount()))
                .min(comparatorByAmount)
                .orElse(PromotionItemEntity.builder().item(item).build());


        System.out.println(promotionItem);

        //        return promotionItemEntities.stream()
//                .map(PromotionItemEntity::getDiscountAmount)
//                .peek(s -> System.out.println(s.getDiscountAmount()))
//                .min(comparatorByAmount)
//                .orElse(null);

        return new PromotionItemResponse().convertEntityToResponse(promotionItem);

    }

}
