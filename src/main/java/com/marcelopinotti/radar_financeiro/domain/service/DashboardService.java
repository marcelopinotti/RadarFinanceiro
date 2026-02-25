package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.Enum.TipoTitulo;
import com.marcelopinotti.radar_financeiro.dto.dashboard.DashboardResponseDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final TituloService tituloService;

    public DashboardResponseDto obterFluxoDeCaixa(Date periodoInicial, Date periodoFinal){
        List<TituloResponseDto> titulos = tituloService.obterPorDataVencimento(periodoInicial,periodoFinal);
        BigDecimal totalApagar = BigDecimal.ZERO;
        BigDecimal totalAreceber = BigDecimal.ZERO;

        List<TituloResponseDto> titulosApagar = new ArrayList<>();
        List<TituloResponseDto> titulosAreceber = new ArrayList<>();

        for (TituloResponseDto titulo : titulos) {
            if (titulo.tipo() == TipoTitulo.A_PAGAR){
                totalApagar = totalApagar.add(titulo.valor());
                titulosApagar.add(titulo);
            } else {
                totalAreceber = totalAreceber.add(titulo.valor());
                titulosAreceber.add(titulo);
            }
        }

        BigDecimal saldo = totalAreceber.subtract(totalApagar);


        return new DashboardResponseDto(totalApagar, totalAreceber, saldo, titulosApagar, titulosAreceber);

    }


}
