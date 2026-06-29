package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.inter.ITradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITradeService tradeService;

    @Test
    void given_tradesExist_when_getList_then_return200() throws Exception {
        given(tradeService.findAll()).willReturn(List.of(new Trade()));

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());

        then(tradeService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    void given_validTrade_when_postValidate_then_redirectToList() throws Exception {
        given(tradeService.save(any())).willReturn(new Trade());
        given(tradeService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection());

        then(tradeService).should().save(any(Trade.class));
    }

    @Test
    void given_invalidTrade_when_postValidate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk());

        then(tradeService).should(never()).save(any());
    }

    @Test
    void given_existingTrade_when_getUpdateForm_then_return200() throws Exception {
        given(tradeService.findById(1)).willReturn(new Trade());

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk());

        then(tradeService).should().findById(1);
    }

    @Test
    void given_validTrade_when_postUpdate_then_redirectToList() throws Exception {
        given(tradeService.update(eq(1), any())).willReturn(new Trade());
        given(tradeService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/trade/update/1")
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection());

        then(tradeService).should().update(eq(1), any(Trade.class));
    }

    @Test
    void given_invalidTrade_when_postUpdate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk());

        then(tradeService).should(never()).update(any(), any());
    }

    @Test
    void given_existingTrade_when_delete_then_redirectToList() throws Exception {
        given(tradeService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(tradeService).should().delete(1);
    }
}
