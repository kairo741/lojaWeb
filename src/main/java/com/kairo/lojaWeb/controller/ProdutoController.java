package com.kairo.lojaWeb.controller;

import com.kairo.lojaWeb.filter.FilterProduto;
import com.kairo.lojaWeb.models.Produto;
import com.kairo.lojaWeb.repositories.CategoriaRepository;
import com.kairo.lojaWeb.repositories.MarcaRepository;
import com.kairo.lojaWeb.repositories.ProdutoRepository;
import com.kairo.lojaWeb.services.produto.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {

    private static String pathImage = "D:\\Usuário\\Documents\\IF 2021\\Desenv WEB\\imagens\\";

    @Autowired
    private ProdutoRepository produtoRepository;

    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoService produtoService;

    @GetMapping("/administrativo/produtos/cadastrar")
    public ModelAndView register(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("marcasList", marcaRepository.findAll());
        mv.addObject("categoriasList", categoriaRepository.findAll());
        return mv;
    }

    @GetMapping("/administrativo/produtos/listar")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("produtosList", produtoRepository.findAll());
        mv.addObject("marcasList", marcaRepository.findAll());
        mv.addObject("categoriasList", categoriaRepository.findAll());
        mv.addObject("filter", new FilterProduto());
        return mv;
    }

    @GetMapping("/administrativo/produtos/listar/filter-by")
    public ModelAndView listFilterBy(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String categoria) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");

        var filter = FilterProduto.builder()
                .nome(nome)
                .marca(marca)
                .categoria(categoria)
                .build();

        mv.addObject("marcasList", marcaRepository.findAll());
        mv.addObject("categoriasList", categoriaRepository.findAll());
        mv.addObject("produtosList", produtoService.findByFilter(filter));
        mv.addObject("filter", new FilterProduto());
        return mv;
    }


    @GetMapping("/administrativo/produtos/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        return register(produtoOptional.get());
    }

    @GetMapping("/administrativo/produtos/remover/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        produtoRepository.delete(produtoOptional.get());
        return list();
    }

    @GetMapping("/administrativo/produtos/remover")
    public ModelAndView removeAll() {
        produtoRepository.deleteAll();
        return list();
    }

    @ResponseBody
    @GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
    public byte[] showImage(@PathVariable("imagem") String imagem) {
        var filePath = new File(pathImage + imagem);

        if (imagem != null || imagem.trim().length() > 0) {
            try {
                return Files.readAllBytes(filePath.toPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @PostMapping("/administrativo/produtos/salvar")
    public ModelAndView save(@Valid Produto produto, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return register(produto);
        }
        produtoRepository.save(produto);

        try {
            if (!file.isEmpty()) {

                var bytes = file.getBytes();
                var fileName = produto.getId() + "_" + file.getOriginalFilename();
                var path = Paths.get(pathImage + fileName);
                Files.write(path, bytes);

                produto.setImageName(fileName);
                produtoRepository.save(produto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list();
    }

}
