package com.ssg.product.controller;


import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.payload.request.PromotionItemInsertRequest;
import com.ssg.product.payload.response.PromotionItemResponse;
import com.ssg.product.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<String> promotionInsert(@RequestBody @Valid PromotionInsertRequest promotionInsertRequest){
        return promotionService.promotionInsert(promotionInsertRequest);

    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<String> promotionDelete(@PathVariable Long promotionId){

        return promotionService.promotionDelete(promotionId);
    }

    @PostMapping("/item")
    public ResponseEntity<String> promotionItemInsert(@RequestBody @Valid PromotionItemInsertRequest request){
        return promotionService.promotionItemInsert(request);
    }

    @GetMapping("/item")
    public PromotionItemResponse promotionItemInformation(@RequestParam Long itemId){
        return promotionService.promotionItemInformation(itemId);
    }

}
