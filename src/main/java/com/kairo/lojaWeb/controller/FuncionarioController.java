package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Funcionario;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/administrativo/funcionarios/cadastrar")
    public ModelAndView register(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
        mv.addObject("funcionariosList", funcionarioRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        return register(funcionarioOptional.get());
    }

    @GetMapping("/administrativo/funcionarios/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        funcionarioRepository.delete(funcionarioOptional.get());
        return list();
    }

    @GetMapping("/administrativo/funcionarios/remover")
    public ModelAndView removeAll() {
        funcionarioRepository.deleteAll();
        return list();
    }

    @PostMapping("/administrativo/funcionarios/salvar")
    public ModelAndView save(@Valid Funcionario funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return register(funcionario);
        }
//        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha())); todo - enriptar senha con o Secutiry
        funcionarioRepository.save(funcionario);
        return list();
    }

}
