package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Papel;
import com.kairo.lojaWeb.repositories.PapelRepository;
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
public class PapelController {

    @Autowired
    private PapelRepository papelRepository;

    @GetMapping("/administrativo/papeis/cadastrar")
    public ModelAndView register(Papel papel) {
        ModelAndView mv = new ModelAndView("administrativo/papeis/cadastro");
        mv.addObject("papel", papel);
        return mv;
    }

    @GetMapping("/administrativo/papeis/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/papeis/lista");
        mv.addObject("papeisList", papelRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/papeis/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Papel> papelOptional = papelRepository.findById(id);
        return register(papelOptional.get());
    }

    @GetMapping("/administrativo/papeis/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Papel> papelOptional = papelRepository.findById(id);
        papelRepository.delete(papelOptional.get());
        return list();
    }

    @GetMapping("/administrativo/papeis/remover")
    public ModelAndView removeAll() {
        papelRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/papeis/salvar")
    public ModelAndView save(@Valid Papel papel, BindingResult result) {
        if (result.hasErrors()) {
            return register(papel);
        }
        papelRepository.save(papel);
        return list();
    }

}
