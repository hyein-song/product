package com.ssg.product.controller;

import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.payload.request.ItemListRequest;
import com.ssg.product.payload.response.ItemResponse;
import com.ssg.product.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> itemInsert(@RequestBody ItemInsertRequest itemInsertRequest){
        return itemService.itemInsert(itemInsertRequest);
    }

    @DeleteMapping("/{item_id}")
    public ResponseEntity<String> itemDelete(@PathVariable Long item_id){
        return itemService.itemDelete(item_id);
    }

    @GetMapping("/list")
    public List<ItemResponse> itemList(@RequestBody ItemListRequest request){
        return itemService.itemList(request);

    }
}
