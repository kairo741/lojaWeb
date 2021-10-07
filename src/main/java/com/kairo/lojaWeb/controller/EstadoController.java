package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Estado;
import com.kairo.lojaWeb.models.Estado;
import com.kairo.lojaWeb.repositories.EstadoRepository;
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
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/administrativo/estados/cadastrar")
    public ModelAndView register(Estado estado) {
        ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
        mv.addObject("estado", estado);
        return mv;
    }

    @GetMapping("/administrativo/estados/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
        mv.addObject("estadosList", estadoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/estados/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        return register(estadoOptional.get());
    }

    @GetMapping("/administrativo/estados/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        estadoRepository.delete(estadoOptional.get());
        return list();
    }

    @GetMapping("/administrativo/estados/remover")
    public ModelAndView removeAll() {
        estadoRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/estados/salvar")
    public ModelAndView save(@Valid Estado estado, BindingResult result) {
        if (result.hasErrors()) {
            return register(estado);
        }
        estadoRepository.save(estado);
        return list();
    }

}
