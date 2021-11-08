package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Funcionario;
import com.kairo.lojaWeb.repositories.CidadeRepository;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import com.kairo.lojaWeb.services.funcionario.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

//    @Autowired
//    private PasswordEncoder encoder;

    private final FuncionarioService service;


    @GetMapping("/administrativo/funcionarios/cadastrar")
    public ModelAndView register(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("cidadesList", cidadeRepository.findAll());
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

        if (result.getAllErrors().size() > 0) {
            log.error(String.valueOf(result.getAllErrors()));
        }

        if (result.hasErrors()) {
            return register(funcionario);
        }

        //Validação do CPF

        var isValid = service.validateCPF(funcionario.getCpf());
        if (!isValid) {
            result.rejectValue("cpf", "error.funcionario", "CPF inválido!");
            return register(funcionario);
        }

//        funcionario.setSenha(encoder.encode(funcionario.getSenha())); // encoder do PasswordEncoder do Spring
        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha())); // encrypt do BCrypt
        funcionarioRepository.save(funcionario);
        return list();
    }

}
