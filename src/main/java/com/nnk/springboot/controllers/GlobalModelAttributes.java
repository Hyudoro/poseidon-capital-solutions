package com.nnk.springboot.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("loggedInUser")
    public String loggedInUser(Principal principal) {
        return principal != null ? principal.getName() : "";
    }
}
