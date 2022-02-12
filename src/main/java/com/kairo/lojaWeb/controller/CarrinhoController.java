package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.Cliente;
import com.kairo.lojaWeb.models.Compra;
import com.kairo.lojaWeb.models.ItensCompra;
import com.kairo.lojaWeb.models.Produto;
import com.kairo.lojaWeb.repositories.ClienteRepository;
import com.kairo.lojaWeb.repositories.CompraRepository;
import com.kairo.lojaWeb.repositories.ItensCompraRepository;
import com.kairo.lojaWeb.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CarrinhoController {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItensCompraRepository itensCompraRepository;
    private List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente = new Cliente();

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        var mv = new ModelAndView("cliente/carrinho");
        calculateFinalPrice();
        mv.addObject("compra", compra);
        mv.addObject("itemList", itensCompras);
        return mv;
    }

    @GetMapping("/payment")
    public ModelAndView payment() {
        getAuthCliente();
        var mv = new ModelAndView("cliente/finalizar");
        calculateFinalPrice();
        mv.addObject("compra", compra);
        mv.addObject("itemList", itensCompras);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @PostMapping("/payment/confirm")
    public ModelAndView confirmPayment(String paymentForm) {
        var mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(paymentForm);
        compra = compraRepository.save(compra);

        itensCompras.forEach(itensCompra -> {
            itensCompra.setCompra(compra);
        });

        itensCompras = new ArrayList<>();
        compra = new Compra();
        return mv;
    }

    @GetMapping("/addCarrinho/{id}")
    public String carrinho(@PathVariable Long id) {
        log.debug("Id do produto: {}", id);
        Optional<Produto> opProduto = produtoRepository.findById(id);

        if (!subtractOrAddProduct(opProduto.get().getId(), "ADD")) {
            var produto = opProduto.get();
            var item = ItensCompra.builder()
                    .produto(produto)
                    .valorUnitario(produto.getValorVenda())
                    .build();
            item.setQuantidade((item.getQuantidade() != null ? item.getQuantidade() : 0) + 1);
            item.setValorTotal(produto.getValorVenda() * item.getQuantidade());
            itensCompras.add(item);
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/changeQuantity/{id}/{actionType}")
    public String changeQuantity(@PathVariable Long id, @PathVariable String actionType) {
        subtractOrAddProduct(id, actionType.toUpperCase());
        return "redirect:/carrinho";
    }

    @GetMapping("/remove/{id}")
    public String removeProdutoCarrinho(@PathVariable Long id) {
        itensCompras.removeIf(item -> Objects.equals(item.getProduto().getId(), id));
        return "redirect:/carrinho";
    }

    private void getAuthCliente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String email = auth.getName();
            cliente = clienteRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        }
    }

    private Boolean subtractOrAddProduct(Long idProduto, String actionType) {
        if (itensCompras.stream().anyMatch(item -> Objects.equals(item.getProduto().getId(), idProduto))) {
            var itemCompra = itensCompras.stream()
                    .filter(item -> Objects.equals(item.getProduto().getId(), idProduto))
                    .findFirst().orElse(null);
            assert itemCompra != null;
            var produto = itemCompra.getProduto();
            if (actionType.equals("ADD")) {
                itemCompra.setQuantidade(itemCompra.getQuantidade() + 1);
            } else if (actionType.equals("SUB")) {
                if (itemCompra.getQuantidade() > 1) {
                    itemCompra.setQuantidade(itemCompra.getQuantidade() - 1);
                } else {
                    itensCompras.removeIf(item -> Objects.equals(item.getProduto().getId(), idProduto));
                }
            }
            itemCompra.setValorTotal(produto.getValorVenda() * itemCompra.getQuantidade());
            return true;
        } else {
            return false;
        }
    }

    private void calculateFinalPrice() {
        AtomicReference<Double> finalPrice = new AtomicReference<>(0.);
        itensCompras.forEach(itensCompra -> finalPrice.updateAndGet(v -> v + itensCompra.getValorTotal()));
        compra.setValorTotal(finalPrice.get());
    }

}
