package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Permissao;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import com.kairo.lojaWeb.repositories.PapelRepository;
import com.kairo.lojaWeb.repositories.PermissaoRepository;
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
public class PermissoesController {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PapelRepository papelRepository;


    @GetMapping("/administrativo/permissoes/cadastrar")
    public ModelAndView register(Permissao permissao) {
        ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
        mv.addObject("permissao", permissao);
        mv.addObject("funcionariosList", funcionarioRepository.findAll());
        mv.addObject("papeisList", papelRepository.findAll());

        return mv;
    }

    @GetMapping("/administrativo/permissoes/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
        mv.addObject("permissoesList", permissaoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/permissoes/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Permissao> permissaoOptional = permissaoRepository.findById(id);
        return register(permissaoOptional.get());
    }

    @GetMapping("/administrativo/permissoes/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Permissao> permissaoOptional = permissaoRepository.findById(id);
        permissaoRepository.delete(permissaoOptional.get());
        return list();
    }

    @GetMapping("/administrativo/permissoes/remover")
    public ModelAndView removeAll() {
        permissaoRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/permissoes/salvar")
    public ModelAndView save(@Valid Permissao permissao, BindingResult result) {
        if (result.hasErrors()) {
            return register(permissao);
        }
        permissaoRepository.save(permissao);
        return list();
    }

}
