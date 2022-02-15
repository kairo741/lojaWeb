package com.kairo.lojaWeb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kairo.lojaWeb.dto.PaymentStatsDTO;
import com.kairo.lojaWeb.models.Compra;
import com.kairo.lojaWeb.repositories.ClienteRepository;
import com.kairo.lojaWeb.repositories.CompraRepository;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PrincipalController {
    ObjectMapper objectMapper = new ObjectMapper();

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    @GetMapping("/administrativo")
    public ModelAndView mainAccess() throws JsonProcessingException {
        ModelAndView mv = new ModelAndView("administrativo/home");

        var numberOfClientes = clienteRepository.count();
        var numberOfFuncionarios = funcionarioRepository.count();

        mv.addObject("payments", objectMapper.writeValueAsString(getPaymentStats()));
        mv.addObject("clientes", numberOfClientes);
        mv.addObject("funcionarios", numberOfFuncionarios);
        return mv;
    }

    private PaymentStatsDTO getPaymentStats() {
        var compras = compraRepository.findAll();
        var creditCardAmount = 0;
        var debitCardAmount = 0;
        var ticketAmount = 0;
        var bankTransferAmount = 0;

        for (Compra compra : compras) {
            if (Objects.equals(compra.getFormaPagamento(), "Cartão de Crédito")) {
                creditCardAmount++;
            } else if (Objects.equals(compra.getFormaPagamento(), "Cartão de Débito")) {
                debitCardAmount++;
            } else if (Objects.equals(compra.getFormaPagamento(), "Boleto")) {
                ticketAmount++;
            } else if (Objects.equals(compra.getFormaPagamento(), "Transferência")) {
                bankTransferAmount++;
            }
        }

        return PaymentStatsDTO.builder()
                .creditCardAmount(creditCardAmount)
                .debitCardAmount(debitCardAmount)
                .ticketAmount(ticketAmount)
                .bankTransferAmount(bankTransferAmount)
                .build();
    }

}
