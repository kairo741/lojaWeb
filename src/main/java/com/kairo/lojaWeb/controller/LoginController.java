package com.kairo.lojaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }

}
