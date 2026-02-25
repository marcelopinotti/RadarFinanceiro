package com.marcelopinotti.radar_financeiro.domain.Enum;

import lombok.Getter;

@Getter
public enum TipoTitulo {
    A_PAGAR("A Pagar"),
    A_RECEBER("A Receber");

    private final String descricao;

    TipoTitulo(String descricao) {
        this.descricao = descricao;
    }
}
