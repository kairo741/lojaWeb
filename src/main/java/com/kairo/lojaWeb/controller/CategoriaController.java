package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Categoria;
import com.kairo.lojaWeb.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/administrativo/categorias/cadastrar")
    public ModelAndView register(Categoria categoria) {
        ModelAndView mv = new ModelAndView("administrativo/categorias/cadastro");
        mv.addObject("categoria", categoria);
        return mv;
    }

    @GetMapping("/administrativo/categorias/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/categorias/lista");
        mv.addObject("categoriasList", categoriaRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/categorias/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return register(categoriaOptional.get());
    }

    @GetMapping("/administrativo/categorias/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        categoriaRepository.delete(categoriaOptional.get());
        return list();
    }

    @GetMapping("/administrativo/categorias/remover")
    public ModelAndView removeAll() {
        categoriaRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/categorias/salvar")
    public ModelAndView save(@Valid Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return register(categoria);
        }
        categoriaRepository.save(categoria);
        return list();
    }

}
