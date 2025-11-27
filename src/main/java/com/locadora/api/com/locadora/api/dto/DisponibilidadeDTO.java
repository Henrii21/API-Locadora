package com.locadora.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadeDTO {
    private Long itemId;
    private int quantidadeEstoque;
    private int emprestados;
    private int disponiveis;
}
