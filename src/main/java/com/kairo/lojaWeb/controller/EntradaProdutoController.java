package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.models.EntradaItens;
import com.kairo.lojaWeb.models.EntradaProduto;
import com.kairo.lojaWeb.models.Produto;
import com.kairo.lojaWeb.repositories.EntradaItensRepository;
import com.kairo.lojaWeb.repositories.EntradaProdutoRepository;
import com.kairo.lojaWeb.repositories.FuncionarioRepository;
import com.kairo.lojaWeb.repositories.ProdutoRepository;
import com.kairo.lojaWeb.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaProdutoController {

    List<EntradaItens> itensList = new ArrayList<EntradaItens>();

    @Autowired
    private EntradaProdutoRepository entradaProdutoRepository;

    @Autowired
    private EntradaItensRepository entradaItensRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/administrativo/entrada/cadastrar")
    public ModelAndView register(EntradaProduto entrada, EntradaItens entradaItens) {
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("itensList", itensList);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("funcionariosList", funcionarioRepository.findAll());
        mv.addObject("produtosList", produtoRepository.findAll());
        return mv;
    }

//    @GetMapping("/administrativo/estados/listar")
//    public ModelAndView list() {
//        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//        mv.addObject("estadosList", entradaProdutoRepository.findAll());
//        return mv;
//    }

//    @GetMapping("/administrativo/estados/editar/{id}")
//    public ModelAndView edit(@PathVariable("id") Long id) {
//        Optional<Estado> estadoOptional = entradaProdutoRepository.findById(id);
//        return register(estadoOptional.get());
//    }

//    @GetMapping("/administrativo/estados/remover/{id}")
//    public ModelAndView remove(@PathVariable("id") Long id) {
//        Optional<Estado> estadoOptional = entradaProdutoRepository.findById(id);
//        entradaProdutoRepository.delete(estadoOptional.get());
//        return list();
//    }

//    @GetMapping("/administrativo/estados/remover")
//    public ModelAndView removeAll() {
//        entradaProdutoRepository.deleteAll();
//        return list();
//    }

    @PostMapping("/administrativo/entrada/salvar")
    public ModelAndView save(String acao, EntradaProduto entrada, EntradaItens entradaItens) {
        if (acao.equals(Constants.ACAO_ITENS)) {
            this.itensList.add(entradaItens);
        } else if (acao.equals(Constants.ACAO_SALVAR)) {
            for (EntradaItens item : itensList) {
                item.setEntrada(entrada);
                entradaItensRepository.saveAndFlush(item);
                Optional<Produto> produtoOptional = produtoRepository.findById(item.getProduto().getId());
                Produto produto = produtoOptional.get();
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
                produto.setValorVenda(item.getValorVenda());
                produtoRepository.saveAndFlush(produto);

            }
            this.itensList = new ArrayList<EntradaItens>();
            return register(new EntradaProduto(), new EntradaItens());
        }

        return register(entrada, new EntradaItens());
    }

}
