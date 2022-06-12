package com.ssg.product.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.product.entity.value.ItemType;
import com.ssg.product.payload.request.ItemInsertRequest;
import com.ssg.product.payload.response.ItemResponse;
import com.ssg.product.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void itemInsert() throws Exception {
        // given
        ResponseEntity<String> response = new ResponseEntity<>("상품 등록 완료", HttpStatus.OK);
        ItemInsertRequest itemInsertRequest = createItemInsertRequest();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        //mocking
        doReturn(response).when(itemService).itemInsert(any(ItemInsertRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemInsertRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void itemDelete() throws Exception {
        //given
        ResponseEntity<String> response = new ResponseEntity<>("상품 정보 삭제 완료", HttpStatus.OK);
        Long itemId = 1L;

        //mocking
        doReturn(response).when(itemService).itemDelete(any(Long.class));

        //then
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/item/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andDo(print());

        //when
        resultActions.andExpect(status().isOk());

    }

    @Test
    void itemList() throws Exception {
        //given
        ItemResponse itemResponse = createItemResponse();
        List<ItemResponse> response = new ArrayList<>();
        response.add(itemResponse);

        //mocking
        doReturn(response).when(itemService).itemList(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/item/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());

    }

    private ItemInsertRequest createItemInsertRequest() {
        return ItemInsertRequest.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }

    private ItemResponse createItemResponse(){
        return ItemResponse.builder()
                .itemName("item1")
                .itemType(ItemType.NORMAL)
                .itemPrice(20000L)
                .itemDisplayStartDate(LocalDate.parse("2022-01-01"))
                .itemDisplayEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }

}