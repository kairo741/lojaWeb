package com.kairo.lojaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
    @GetMapping("/administrativo")
    public String mainAccess() {
        return "administrativo/home";
    }

}
