package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Funcionario;
import com.kairo.lojaWeb.repositories.CidadeRepository;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import com.kairo.lojaWeb.services.auth.AuthService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final AuthService authService;

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
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        if (result.getAllErrors().size() > 0) {
            log.error(String.valueOf(result.getAllErrors()));
        }

        if (result.hasErrors()) {
            return register(funcionario);
        }
        String randomPassword = UUID.randomUUID().toString();

//        funcionario.setSenha(encoder.encode(funcionario.getSenha())); // encoder do PasswordEncoder do Spring
        funcionario.setSenha(new BCryptPasswordEncoder().encode(randomPassword)); // encrypt do BCrypt
        var funcionarioEntity = funcionarioRepository.save(funcionario);

        try {
            authService.sendEmailPassword(funcionarioEntity, randomPassword);
            return list();
        } catch (Exception e) {
            mv.addObject("funcionario", funcionario);
            mv.addObject("cidadesList", cidadeRepository.findAll());
            mv.addObject("error", "Ocorreu um erro ao enviar o email!");
        }
        return mv;
    }

}
