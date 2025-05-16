package com.tnbin.puttly.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("pages/index");
    }

    @GetMapping("/sign-up")
    public ModelAndView signUp() {
        return new ModelAndView("pages/user/signup");
    }
}