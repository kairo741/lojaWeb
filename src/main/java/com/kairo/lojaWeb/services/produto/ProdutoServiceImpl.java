package com.kairo.lojaWeb.services.produto;

import com.kairo.lojaWeb.filter.FilterProduto;
import com.kairo.lojaWeb.models.Produto;
import com.kairo.lojaWeb.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    @Override
    public List<Produto> findByFilter(FilterProduto filter) {

        var produtosList = repository.findAllByCategoria_NomeAndDescricaoAndMarca_Nome(filter.getCategoria(), filter.getNome(), filter.getMarca());

        return produtosList;

    }
}
