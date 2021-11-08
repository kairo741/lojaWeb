package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Marca;
import com.kairo.lojaWeb.repositories.MarcaRepository;
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
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/administrativo/marcas/cadastrar")
    public ModelAndView register(Marca marca) {
        ModelAndView mv = new ModelAndView("administrativo/marcas/cadastro");
        mv.addObject("marca", marca);
        return mv;
    }

    @GetMapping("/administrativo/marcas/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/marcas/lista");
        mv.addObject("marcasList", marcaRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/marcas/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        return register(marcaOptional.get());
    }

    @GetMapping("/administrativo/marcas/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        marcaRepository.delete(marcaOptional.get());
        return list();
    }

    @GetMapping("/administrativo/marcas/remover")
    public ModelAndView removeAll() {
        marcaRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/marcas/salvar")
    public ModelAndView save(@Valid Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            return register(marca);
        }
        marcaRepository.save(marca);
        return list();
    }

}
