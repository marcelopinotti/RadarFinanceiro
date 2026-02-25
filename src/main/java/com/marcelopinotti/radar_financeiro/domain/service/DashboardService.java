package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.Enum.TipoTitulo;
import com.marcelopinotti.radar_financeiro.dto.dashboard.DashboardResponseDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {
    @Autowired
    private TituloService tituloService;

    public DashboardResponseDto obterFluxoDeCaixa(Date periodoInicial, Date periodoFinal){
        List<TituloResponseDto> titulos = tituloService.obterPorDataVencimento(periodoInicial,periodoFinal);
        Locale locale = new Locale("pt", "BR");
        BigDecimal totalApagar = BigDecimal.ZERO;
        BigDecimal totalAreceber = BigDecimal.ZERO;
        BigDecimal saldo;


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

        saldo = totalAreceber.subtract(totalApagar);

        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        String formatadoPagar = nf.format(totalApagar);
        String formatadoReceber = nf.format(totalAreceber);
        String formatadoSaldo = nf.format(saldo);
        return new DashboardResponseDto(totalApagar, totalAreceber, saldo, titulosApagar, titulosAreceber);

    }


}
