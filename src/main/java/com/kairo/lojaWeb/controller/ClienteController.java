package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Cliente;
import com.kairo.lojaWeb.repositories.CidadeRepository;
import com.kairo.lojaWeb.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ClienteController {

    private final CidadeRepository cidadeRepository;
    private final ClienteRepository clienteRepository;

    @GetMapping("/cliente/cadastrar")
    public ModelAndView register(Cliente cliente) {
        var mv = new ModelAndView("cliente/cadastrar");
        mv.addObject("cliente", cliente);
        mv.addObject("cidadeList", cidadeRepository.findAll());
        return mv;
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Cliente> opCliente = clienteRepository.findById(id);
        return register(opCliente.get());
    }

    @PostMapping("/cliente/salvar")
    public ModelAndView save(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return register(cliente);
        }
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha())); // encrypt do BCrypt
        clienteRepository.save(cliente);
        return register(new Cliente());
    }

}