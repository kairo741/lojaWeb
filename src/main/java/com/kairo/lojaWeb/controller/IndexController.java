package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class IndexController {

    @Autowired
    private ProdutoRepository produtoRepository;

    private static String pathImage = "D:\\Usuário\\Documents\\IF 2021\\Desenv WEB\\imagens\\";

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("produtosList", produtoRepository.findAll());
        return mv;
    }


    @ResponseBody
    @GetMapping("/mostrarImagem/{imagem}")
    public byte[] showImage(@PathVariable("imagem") String imagem) {
        var filePath = new File(pathImage + imagem);

        if (imagem != null || imagem.trim().length() > 0) {
            try {
                return Files.readAllBytes(filePath.toPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
