package com.ssg.product.controller;

import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.payload.response.ItemResponse;
import com.ssg.product.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> itemInsert(@RequestBody @Valid ItemInsertRequest itemInsertRequest){
        return itemService.itemInsert(itemInsertRequest);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> itemDelete(@PathVariable Long itemId){
        return itemService.itemDelete(itemId);
    }

    @GetMapping("/list")
    public List<ItemResponse> itemList(@RequestParam Long userId){
        return itemService.itemList(userId);

    }


}
