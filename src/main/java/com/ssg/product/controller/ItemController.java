package com.ssg.product.controller;

import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> itemInsert(@RequestBody ItemInsertRequest itemInsertRequest){
        return itemService.itemInsert(itemInsertRequest);
    }
}
