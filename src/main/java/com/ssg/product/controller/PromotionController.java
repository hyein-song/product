package com.ssg.product.controller;


import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<String> promotionInsert(@RequestBody PromotionInsertRequest promotionInsertRequest){
        return promotionService.promotionInsert(promotionInsertRequest);

    }

    @DeleteMapping("/delete/{promotion_id}")
    public ResponseEntity<String> promotionDelete(@PathVariable Long promotion_id){

        return promotionService.promotionDelete(promotion_id);
    }
}
