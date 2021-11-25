package com.kairo.lojaWeb.filter;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterProduto extends FilterGeneral {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String marca;

    private String categoria;

}
