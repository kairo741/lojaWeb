package com.kairo.lojaWeb.services.produto;

import com.kairo.lojaWeb.filter.FilterProduto;
import com.kairo.lojaWeb.models.Produto;

import java.util.List;

public interface ProdutoService {


    List<Produto> findByFilter(FilterProduto filter);

}
