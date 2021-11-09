package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        var mv = new ModelAndView("/recuperar-senha/validate-code");
        mv.addObject("email", email);
        return mv;
    }

    @PostMapping("/validate-code/{email}")
    public ModelAndView validateCode(@PathVariable("email") String email, String code) {
        var mv = new ModelAndView("/recuperar-senha/validate-code");
        try {

            var isValid = authService.validateCode(email, code);

            if (isValid) {
                var newPasswordMV = new ModelAndView("/recuperar-senha/new-password");
                newPasswordMV.addObject("email", email);
                return newPasswordMV;
            } else {
                mv.addObject("error", "Código inválido!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    @PostMapping("/new-password/{email}")
    public ModelAndView newPassword(String password, String confirmPassword, @PathVariable("email") String email) {
        var mv = new ModelAndView("/recuperar-senha/new-password");
        mv.addObject("password", password);
        mv.addObject("confirmPassword", confirmPassword);
        try {
            if (password.equals(confirmPassword)) {
                authService.newPassword(email, password);
                return new ModelAndView("login");
            } else {
                mv.addObject("error", "As senhas não são iguais!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

}
