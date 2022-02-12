package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.ItensCompra;
import com.kairo.lojaWeb.models.Produto;
import com.kairo.lojaWeb.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CarrinhoController {

    private final List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();

    private final ProdutoRepository produtoRepository;


    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        var mv = new ModelAndView("cliente/carrinho");
        mv.addObject("itemList", itensCompras);
        return mv;
    }

    @GetMapping("/addCarrinho/{id}")
    public ModelAndView carrinho(@PathVariable Long id) {
        var mv = new ModelAndView("cliente/carrinho");

        log.debug("Id do produto: {}", id);
        Optional<Produto> opProduto = produtoRepository.findById(id);

        if (!subtractOrAddProduct(opProduto.get().getId(), "ADD")) {
            var item = ItensCompra.builder()
                    .produto(opProduto.get())
                    .valorUnitario(opProduto.get().getValorVenda())
                    .build();
            item.setQuantidade((item.getQuantidade() != null ? item.getQuantidade() : 0) + 1);
            itensCompras.add(item);
        }

        mv.addObject("itemList", itensCompras);
        return mv;
    }

    @GetMapping("/changeQuantity/{id}/{actionType}")
    public ModelAndView changeQuantity(@PathVariable Long id, @PathVariable String actionType) {
        var mv = new ModelAndView("cliente/carrinho");
        subtractOrAddProduct(id, actionType.toUpperCase());
        mv.addObject("itemList", itensCompras);
        return mv;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeProdutoCarrinho(@PathVariable Long id) {
        var mv = new ModelAndView("cliente/carrinho");

        itensCompras.removeIf(item -> Objects.equals(item.getProduto().getId(), id));

        mv.addObject("itemList", itensCompras);
        return mv;
    }


    private Boolean subtractOrAddProduct(Long idProduto, String actionType) {
        if (itensCompras.stream().anyMatch(item -> Objects.equals(item.getProduto().getId(), idProduto))) {
            var itemCompra = itensCompras.stream()
                    .filter(item -> Objects.equals(item.getProduto().getId(), idProduto))
                    .findFirst().orElse(null);
            assert itemCompra != null;
            if (actionType.equals("ADD")) {
                itemCompra.setQuantidade(itemCompra.getQuantidade() + 1);
            } else if (actionType.equals("SUB")) {
                if (itemCompra.getQuantidade() > 1) {
                    itemCompra.setQuantidade(itemCompra.getQuantidade() - 1);
                } else {
                    itensCompras.removeIf(item -> Objects.equals(item.getProduto().getId(), idProduto));
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
