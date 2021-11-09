package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthService authService;

    @GetMapping("/login")
    public ModelAndView register() {
        return new ModelAndView("/login");
    }

    @GetMapping("/password-recovery")
    public ModelAndView passwordRecovery() {
        return new ModelAndView("/recuperar-senha/send-email");
    }

    @PostMapping("/send-email")
    public ModelAndView sendEmail(String email) {

        try {
            authService.sendEmailCode(email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("/recuperar-senha/password-recovery");
    }


}
