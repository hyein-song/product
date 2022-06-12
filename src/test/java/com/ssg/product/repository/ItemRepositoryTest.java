package com.ssg.product.repository;

import com.ssg.product.entity.ItemEntity;
import com.ssg.product.entity.value.ItemType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void saveItemTest() {

        //given
        ItemEntity itemEntity = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        //when
        ItemEntity result = itemRepository.save(itemEntity);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getItemName()).isEqualTo("item1");
        assertThat(result.getItemType()).isEqualTo(ItemType.NORMAL);
        assertThat(result.getItemPrice()).isEqualTo(20000L);
        assertThat(result.getItemDisplayStartDate()).isEqualTo(LocalDate.parse("2022-01-01"));
        assertThat(result.getItemDisplayEndDate()).isEqualTo(LocalDate.parse("2023-01-01"));

    }

    @Test
    void deleteItemTest(){
        //given
        ItemEntity itemEntity = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        //when
        ItemEntity savedItem = itemRepository.save(itemEntity);
        Optional<ItemEntity> item = itemRepository.findById(savedItem.getId());
        assertThat(item).isPresent();

        item.ifPresent(selectedItem -> {itemRepository.delete(selectedItem);});
        Optional<ItemEntity> result = itemRepository.findById(savedItem.getId());

        //then
        assertThat(result).isEmpty();

    }

    @Test
    void findByItemDisplayStartDateLessThanEqualAndItemDisplayEndDateGreaterThanEqualTest(){
        //given
        ItemEntity itemEntity1 = ItemEntity.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
        ItemEntity itemEntity2 = ItemEntity.builder() // 기간 만료
                .itemName("item2")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2021-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2022-01-01"))
                .build();
        ItemEntity itemEntity3 = ItemEntity.builder()
                .itemName("item3")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();

        ItemEntity savedItem1 = itemRepository.save(itemEntity1);
        ItemEntity savedItem2 = itemRepository.save(itemEntity2);
        ItemEntity savedItem3 = itemRepository.save(itemEntity3);

        assertThat(savedItem1).isNotNull();
        assertThat(savedItem2).isNotNull();
        assertThat(savedItem3).isNotNull();
        LocalDate nowDate = LocalDate.now();

        //when
        List<ItemEntity> itemEntities = itemRepository.findByItemDisplayStartDateLessThanEqualAndItemDisplayEndDateGreaterThanEqual(nowDate,nowDate);

        //then
        assertThat(itemEntities).isNotNull();
        assertThat(itemEntities.size()).isEqualTo(2);

    }


}