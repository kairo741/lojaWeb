package com.kairo.lojaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarrinhoController {

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        return new ModelAndView("cliente/carrinho");
    }

}
