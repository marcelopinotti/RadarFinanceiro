package com.marcelopinotti.radar_financeiro.dto.titulo;

import com.marcelopinotti.radar_financeiro.domain.Enum.TipoTitulo;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record TituloResponseDto(
        Long id,
        String descricao,
        BigDecimal valor,
        Date dataVencimento,
        Date dataPagamento,
        TipoTitulo tipo,
        List<CentroDeCustoResponseDto> centroDeCusto)  {
}
