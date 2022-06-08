package com.ssg.product.controller;


import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.payload.request.PromotionItemInsertRequest;
import com.ssg.product.payload.response.PromotionItemResponse;
import com.ssg.product.service.PromotionService;
import lombok.Getter;
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

    @DeleteMapping("/{promotion_id}")
    public ResponseEntity<String> promotionDelete(@PathVariable Long promotion_id){

        return promotionService.promotionDelete(promotion_id);
    }

    @PostMapping("/item")
    public ResponseEntity<String> promotionItemInsert(@RequestBody PromotionItemInsertRequest request){
        return promotionService.promotionItemInsert(request);
    }

    @GetMapping("/item")
    public PromotionItemResponse promotionItemInformation(@RequestParam Long item_id){
        return promotionService.promotionItemInformation(item_id);
    }

}
