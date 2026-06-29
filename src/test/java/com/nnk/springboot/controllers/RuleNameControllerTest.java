package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.inter.IRuleNameService;
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

@WebMvcTest(RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRuleNameService ruleNameService;

    @Test
    void given_ruleNamesExist_when_getList_then_return200() throws Exception {
        given(ruleNameService.findAll()).willReturn(List.of(new RuleName()));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());

        then(ruleNameService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    void when_postValidate_then_redirectToList() throws Exception {
        given(ruleNameService.save(any())).willReturn(new RuleName());
        given(ruleNameService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(status().is3xxRedirection());

        then(ruleNameService).should().save(any(RuleName.class));
    }

    @Test
    void given_existingRuleName_when_getUpdateForm_then_return200() throws Exception {
        given(ruleNameService.findById(1)).willReturn(new RuleName());

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk());

        then(ruleNameService).should().findById(1);
    }

    @Test
    void when_postUpdate_then_redirectToList() throws Exception {
        given(ruleNameService.update(eq(1), any())).willReturn(new RuleName());
        given(ruleNameService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/ruleName/update/1"))
                .andExpect(status().is3xxRedirection());

        then(ruleNameService).should().update(eq(1), any(RuleName.class));
    }

    @Test
    void given_existingRuleName_when_delete_then_redirectToList() throws Exception {
        given(ruleNameService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(ruleNameService).should().delete(1);
    }
}
