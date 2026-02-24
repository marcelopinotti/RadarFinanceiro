package com.marcelopinotti.radar_financeiro.dto.titulo;

import com.marcelopinotti.radar_financeiro.domain.Enum.TipoTitulo;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoRequestDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record TituloRequestDto(
        Long id,
        String descricao,
        BigDecimal valor,
        Date dataVencimento,
        Date dataPagamento,
        TipoTitulo tipo,
        List<CentroDeCustoRequestDto> centroDeCusto) {
}
