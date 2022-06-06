package com.ssg.product.service;

import com.ssg.product.entity.PromotionEntity;
import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

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
}
