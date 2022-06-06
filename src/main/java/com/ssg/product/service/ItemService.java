package com.ssg.product.service;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.UserEntity;
import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.payload.request.ItemListRequest;
import com.ssg.product.payload.response.ItemResponse;
import com.ssg.product.repository.ItemRepository;
import com.ssg.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public ResponseEntity<String> itemDelete(Long item_id){

        ItemEntity itemEntity = itemRepository.findById(item_id).orElseThrow(()->
                new NullPointerException("해당 id를 가진 상품 정보를 찾지 못했습니다."));

        itemRepository.delete(itemEntity);

        return new ResponseEntity<String>("상품 정보 삭제 완료",HttpStatus.OK);
    }

    @Transactional
    public List<ItemResponse> itemList(ItemListRequest request){
        UserEntity user = userRepository.findByIdAndUserStat(request.getUserId(),"정상").orElseThrow(()->
                new NullPointerException("해당 id를 가진 user가 존재하지 않거나, 탈퇴한 user입니다."));

        // enum으로 변경 필요
        String itemType = "";
        if (user.getUserType().equals("일반")){
            itemType = "일반";
        } else{
            itemType = "기업회원상품";
        }

        // 기간, item type
        List<ItemEntity> itemEntities = itemRepository.findByItemType(itemType);

        return itemEntities.stream()
                .filter(ItemEntity::checkDisplayDate)
                .map(itemEntity -> new ItemResponse().convertEntityToResponse(itemEntity))
                .collect(Collectors.toList());
    }
}
