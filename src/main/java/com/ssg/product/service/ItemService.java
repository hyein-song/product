package com.ssg.product.service;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<String> itemInsert(ItemInsertRequest itemInsertRequest){

        ItemEntity itemEntity = ItemEntity.builder()
                .itemName(itemInsertRequest.getItemName())
                .itemType(itemInsertRequest.getItemType())
                .itemPrice(itemInsertRequest.getItemPrice())
                .itemDisplayStartDate(itemInsertRequest.getItemDisplayStartDate())
                .itemDisplayEndDate(itemInsertRequest.getItemDisplayEndDate())
                .build();

        itemRepository.save(itemEntity);

        return new ResponseEntity<String>("상품 등록이 완료되었습니다.", HttpStatus.OK);
    }
}
