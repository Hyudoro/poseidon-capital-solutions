package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.inter.IRatingService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRatingService ratingService;

    @Test
    void given_ratingsExist_when_getList_then_return200() throws Exception {
        given(ratingService.findAll()).willReturn(List.of(new Rating()));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());

        then(ratingService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    void when_postValidate_then_redirectToList() throws Exception {
        given(ratingService.save(any())).willReturn(new Rating());
        given(ratingService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/rating/validate"))
                .andExpect(status().is3xxRedirection());

        then(ratingService).should().save(any(Rating.class));
    }

    @Test
    void given_existingRating_when_getUpdateForm_then_return200() throws Exception {
        given(ratingService.findById(1)).willReturn(new Rating());

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk());

        then(ratingService).should().findById(1);
    }

    @Test
    void when_postUpdate_then_redirectToList() throws Exception {
        given(ratingService.update(eq(1), any())).willReturn(new Rating());
        given(ratingService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/rating/update/1"))
                .andExpect(status().is3xxRedirection());

        then(ratingService).should().update(eq(1), any(Rating.class));
    }

    @Test
    void given_existingRating_when_delete_then_redirectToList() throws Exception {
        given(ratingService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(ratingService).should().delete(1);
    }
}
