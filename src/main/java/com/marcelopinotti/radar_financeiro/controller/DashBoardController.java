package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.DashboardService;
import com.marcelopinotti.radar_financeiro.dto.dashboard.DashboardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDto> obterDashboard(
            @RequestParam(value = "periodoInicial")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date periodoInicial,

            @RequestParam(value = "periodoFinal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date periodoFinal
    ) {
        return ResponseEntity.ok(dashboardService.obterFluxoDeCaixa(periodoInicial, periodoFinal)
        );

    }
}
