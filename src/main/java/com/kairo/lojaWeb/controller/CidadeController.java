package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Cidade;
import com.kairo.lojaWeb.repositories.CidadeRepository;
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
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/administrativo/cidades/cadastrar")
    public ModelAndView register(Cidade cidade) {
        ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
        mv.addObject("cidade", cidade);
        mv.addObject("estadosList", estadoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/cidades/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
        mv.addObject("cidadesList", cidadeRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/cidades/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Cidade> cidadeOptional = cidadeRepository.findById(id);
        return register(cidadeOptional.get());
    }

    @GetMapping("/administrativo/cidades/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Cidade> cidadeOptional = cidadeRepository.findById(id);
        cidadeRepository.delete(cidadeOptional.get());
        return list();
    }

    @GetMapping("/administrativo/cidades/remover")
    public ModelAndView removeAll() {
        cidadeRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/cidades/salvar")
    public ModelAndView save(@Valid Cidade cidade, BindingResult result) {
        if (result.hasErrors()) {
            return register(cidade);
        }
        cidadeRepository.save(cidade);
        return list();
    }

}
