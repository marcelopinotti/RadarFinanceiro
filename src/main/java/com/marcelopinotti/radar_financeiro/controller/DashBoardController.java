package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.DashboardService;
import com.marcelopinotti.radar_financeiro.dto.dashboard.DashboardResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Operacoes relacionadas ao dashboard")
public class DashBoardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Obter dashboard", description = "Retorna o fluxo de caixa para o periodo informado.")
    @ApiResponse(responseCode = "200", description = "Dashboard retornado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DashboardResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Parametros invalidos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping
    public ResponseEntity<DashboardResponseDto> obterDashboard(
            @Parameter(description = "Data inicial do periodo")
            @RequestParam(value = "periodoInicial")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date periodoInicial,

            @Parameter(description = "Data final do periodo")
            @RequestParam(value = "periodoFinal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date periodoFinal
    ) {
        return ResponseEntity.ok(dashboardService.obterFluxoDeCaixa(periodoInicial, periodoFinal)
        );

    }
}
