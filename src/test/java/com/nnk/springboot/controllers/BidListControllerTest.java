package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.inter.IBidListService;
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

@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc(addFilters = false)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBidListService bidListService;

    @Test
    void given_bidListsExist_when_getList_then_return200() throws Exception {
        given(bidListService.findAll()).willReturn(List.of(new BidList("A", "T", 10d)));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());

        then(bidListService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    void given_validBidList_when_postValidate_then_redirectToList() throws Exception {
        given(bidListService.save(any())).willReturn(new BidList("A", "T", 10d));
        given(bidListService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection());

        then(bidListService).should().save(any(BidList.class));
    }

    @Test
    void given_invalidBidList_when_postValidate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk());

        then(bidListService).should(never()).save(any());
    }

    @Test
    void given_existingBid_when_getUpdateForm_then_return200() throws Exception {
        given(bidListService.findById(1)).willReturn(new BidList("A", "T", 10d));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk());

        then(bidListService).should().findById(1);
    }

    @Test
    void given_validBidList_when_postUpdate_then_redirectToList() throws Exception {
        given(bidListService.update(eq(1), any())).willReturn(new BidList("A", "T", 10d));
        given(bidListService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection());

        then(bidListService).should().update(eq(1), any(BidList.class));
    }

    @Test
    void given_invalidBidList_when_postUpdate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk());

        then(bidListService).should(never()).update(any(), any());
    }

    @Test
    void given_existingBid_when_delete_then_redirectToList() throws Exception {
        given(bidListService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(bidListService).should().delete(1);
    }
}
