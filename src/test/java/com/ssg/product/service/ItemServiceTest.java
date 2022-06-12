package com.ssg.product.service;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.UserEntity;
import com.ssg.product.entity.value.ItemType;
import com.ssg.product.entity.value.UserType;
import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.payload.response.ItemResponse;
import com.ssg.product.repository.ItemRepository;
import com.ssg.product.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void itemInsert() {
        //given
        ItemInsertRequest itemInsertRequest = createitemInsertRequest();
        ItemEntity itemEntity = createItemEntity(itemInsertRequest);

        //mocking
        doReturn(itemEntity).when(itemRepository).save(any(ItemEntity.class));

        //when
        ResponseEntity<String> result = itemService.itemInsert(itemInsertRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("상품 등록 완료");

    }

    @Test
    void itemDelete() {
        //given
        ItemEntity itemEntity = ItemEntity.builder().build();

        //mocking
        doReturn(Optional.of(itemEntity)).when(itemRepository).findById(1L);

        //when
        ResponseEntity<String> result = itemService.itemDelete(1L);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("상품 정보 삭제 완료");


    }

    @Test
    void itemList_normalUser() {
        //given
        UserEntity user = createNormalUser();
        ReflectionTestUtils.setField(user,"id",1L);
        List<ItemEntity> itemEntities = createItemList();
        LocalDate nowDate = LocalDate.now();

        //mocking
        doReturn(Optional.of(user)).when(userRepository).findByIdAndUserStat(user.getId(),user.getUserStat());
        doReturn(itemEntities).when(itemRepository).findByItemDisplayStartDateLessThanEqualAndItemDisplayEndDateGreaterThanEqual(nowDate,nowDate);

        //when
        List<ItemResponse> result = itemService.itemList(1L);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void itemList_corporateUser() {
        //given
        UserEntity user = createCorporateUser();
        ReflectionTestUtils.setField(user,"id",1L);
        List<ItemEntity> itemEntities = createItemList();
        LocalDate nowDate = LocalDate.now();

        //mocking
        doReturn(Optional.of(user)).when(userRepository).findByIdAndUserStat(user.getId(),user.getUserStat());
        doReturn(itemEntities).when(itemRepository).findByItemDisplayStartDateLessThanEqualAndItemDisplayEndDateGreaterThanEqual(nowDate,nowDate);

        //when
        List<ItemResponse> result = itemService.itemList(1L);

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    private ItemEntity createItemEntity(ItemInsertRequest itemInsertRequest){
        return ItemEntity.builder()
                .itemName(itemInsertRequest.getItemName())
                .itemType(itemInsertRequest.getItemType())
                .itemPrice(itemInsertRequest.getItemPrice())
                .itemDisplayStartDate(itemInsertRequest.getItemDisplayStartDate())
                .itemDisplayEndDate(itemInsertRequest.getItemDisplayEndDate())
                .build();
    }

    private ItemInsertRequest createitemInsertRequest(){
        return ItemInsertRequest.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }

    private List<ItemEntity> createItemList(){
        ItemEntity itemEntity1 = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
        ItemEntity itemEntity2 = ItemEntity.builder()
                .itemName("item2")
                .itemType(ItemType.CORPORATE)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
        ItemEntity itemEntity3 = ItemEntity.builder()
                .itemName("item3")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(itemEntity1);
        itemEntities.add(itemEntity2);
        itemEntities.add(itemEntity3);

        return itemEntities;
    }

    private UserEntity createNormalUser(){
        return UserEntity.builder()
                .userName("홍길동")
                .userType(UserType.NORMAL)
                .userStat("정상")
                .build();

    }

    private UserEntity createCorporateUser(){
        return UserEntity.builder()
                .userName("홍길동")
                .userType(UserType.CORPORATE)
                .userStat("정상")
                .build();

    }



}