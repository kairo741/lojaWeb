package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Funcionario;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/administrativo/funcionarios/cadastrar")
    public ModelAndView register (Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/listar")
    public String list() {
        return "administrativo/funcionarios/lista";
    }

    @PostMapping("/administrativo/funcionarios/salvar")
    public ModelAndView save (@Valid Funcionario funcionario, BindingResult result) {

        if(result.hasErrors()){
            return register(funcionario);
        }
        funcionarioRepository.save(funcionario);
        return register(new Funcionario());
    }

}
