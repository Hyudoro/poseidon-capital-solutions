package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.inter.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    void given_usersExist_when_getList_then_return200() throws Exception {
        given(userService.findAll()).willReturn(List.of(new User()));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());

        then(userService).should().findAll();
    }

    @Test
    void when_getAddForm_then_return200() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    void given_validUser_when_postValidate_then_redirectToList() throws Exception {
        given(userService.save(any())).willReturn(new User());
        given(userService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/user/validate")
                        .param("username", "alice")
                        .param("password", "Password1!")
                        .param("fullname", "Alice Smith")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection());

        then(userService).should().save(any(User.class));
    }

    @Test
    void given_invalidUser_when_postValidate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk());

        then(userService).should(never()).save(any());
    }

    @Test
    void given_existingUser_when_getUpdateForm_then_passwordIsCleared() throws Exception {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("encodedSecret");
        user.setFullname("Alice");
        user.setRole("USER");
        given(userService.findById(1)).willReturn(user);

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk());

        assertThat(user.getPassword()).isEmpty();
    }

    @Test
    void given_validUser_when_postUpdate_then_redirectToList() throws Exception {
        given(userService.update(eq(1), any())).willReturn(new User());
        given(userService.findAll()).willReturn(List.of());

        mockMvc.perform(post("/user/update/1")
                        .param("username", "alice")
                        .param("password", "Password1!")
                        .param("fullname", "Alice Smith")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection());

        then(userService).should().update(eq(1), any(User.class));
    }

    @Test
    void given_invalidUser_when_postUpdate_then_return200WithErrors() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk());

        then(userService).should(never()).update(any(), any());
    }

    @Test
    void given_existingUser_when_delete_then_redirectToList() throws Exception {
        given(userService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection());

        then(userService).should().delete(1);
    }
}
