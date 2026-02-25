package com.marcelopinotti.radar_financeiro.dto.dashboard;

import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponseDto(
        BigDecimal totalApagar,
        BigDecimal totalAreceber,
        BigDecimal saldo,
        List<TituloResponseDto> titulosApagar,
        List<TituloResponseDto> titulosAreceber
        ) {

}
