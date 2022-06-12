package com.ssg.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssg.product.payload.request.PromotionInsertRequest;
import com.ssg.product.payload.request.PromotionItemInsertRequest;
import com.ssg.product.payload.response.PromotionItemResponse;
import com.ssg.product.service.PromotionService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PromotionController.class)
class PromotionControllerTest {

    @MockBean
    PromotionService promotionService;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void promotionInsert() throws Exception {
        //given
        ResponseEntity<String> response = new ResponseEntity<>("프로모션 정보 저장 완료", HttpStatus.OK);
        PromotionInsertRequest promotionInsertRequest = createPromotionInsertRequest();

        //mocking
        doReturn(response).when(promotionService).promotionInsert(any(PromotionInsertRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/promotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(promotionInsertRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    void promotionDelete() throws Exception{
        //given
        ResponseEntity<String> response = new ResponseEntity<>("프로모션 정보 삭제 완료", HttpStatus.OK);
        Long fakeId = 1L;

        //mocking
        doReturn(response).when(promotionService).promotionDelete(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/promotion/{promotionId}", fakeId)

        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    void promotionItemInsert() throws Exception {
        //given
        ResponseEntity<String> response = new ResponseEntity<>("프로모션상품 등록 완료", HttpStatus.OK);

        PromotionItemInsertRequest promotionItemInsertRequest = PromotionItemInsertRequest.builder()
                .promotionId(1L)
                .itemId(1L)
                .build();

        //mocking
        doReturn(response).when(promotionService).promotionItemInsert(any(PromotionItemInsertRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/promotion/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(promotionItemInsertRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void promotionItemInformation() throws Exception {
        //given
        PromotionItemResponse promotionItemResponse = PromotionItemResponse.builder().build();

        //mocking
        doReturn(promotionItemResponse).when(promotionService).promotionItemInformation(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/promotion/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId","1")

        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    private PromotionInsertRequest createPromotionInsertRequest() {
        return PromotionInsertRequest.builder()
                .promotionNm("promotion1")
                .discountAmount(1000L)
                .promotionStartDate(LocalDate.parse("2022-01-01"))
                .promotionEndDate(LocalDate.parse("2023-01-01"))
                .build();
    }
}