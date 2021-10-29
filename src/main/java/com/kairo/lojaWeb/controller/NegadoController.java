package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Cidade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoController {

    @GetMapping("/negado")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("/negado");
        return mv;
    }

}
