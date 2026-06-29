package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.inter.ICurvePointService;
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

@WebMvcTest(CurveController.class)
@AutoConfigureMockMvc(addFilters = false)
class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurvePointService curvePointService;

    @Test
    void given_curvePointsExist_when_getList_then_return200() throws Exception {
        given(curvePointService.findAll()).willReturn(List.of(new CurvePoint()));

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());

        then(curvePointService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    void when_postValidate_then_redirectToList() throws Exception {
        given(curvePointService.save(any())).willReturn(new CurvePoint());
        given(curvePointService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/curvePoint/validate"))
                .andExpect(status().is3xxRedirection());

        then(curvePointService).should().save(any(CurvePoint.class));
    }

    @Test
    void given_existingCurvePoint_when_getUpdateForm_then_return200() throws Exception {
        given(curvePointService.findById(1)).willReturn(new CurvePoint());

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk());

        then(curvePointService).should().findById(1);
    }

    @Test
    void when_postUpdate_then_redirectToList() throws Exception {
        given(curvePointService.update(eq(1), any())).willReturn(new CurvePoint());
        given(curvePointService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/curvePoint/update/1"))
                .andExpect(status().is3xxRedirection());

        then(curvePointService).should().update(eq(1), any(CurvePoint.class));
    }

    @Test
    void given_existingCurvePoint_when_delete_then_redirectToList() throws Exception {
        given(curvePointService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(curvePointService).should().delete(1);
    }
}
