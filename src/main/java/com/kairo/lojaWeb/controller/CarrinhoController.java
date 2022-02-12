package com.kairo.lojaWeb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class CarrinhoController {

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        return new ModelAndView("cliente/carrinho");
    }

    @GetMapping("/addCarrinho/{id}")
    public ModelAndView carrinho(@PathVariable Long id) {
        log.debug("Id do produto: {}", id);
        return new ModelAndView("cliente/carrinho");
    }

}
