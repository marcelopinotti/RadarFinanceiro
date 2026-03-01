package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.CentroDeCustoService;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoRequestDto;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/centro-de-custo")
@Tag(name = "Centro de Custo", description = "Operacoes relacionadas a centros de custo")
public class CentroDeCustoController {


    private final CentroDeCustoService centroDeCustoService;

    @Operation(summary = "Listar centros de custo", description = "Retorna todos os centros de custo cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CentroDeCustoResponseDto.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping(value = "/obter")
    public ResponseEntity<List<CentroDeCustoResponseDto>> obterTodos() {
        return ResponseEntity.ok(centroDeCustoService.obterTodos());
    }

    @Operation(summary = "Buscar centro de custo por id", description = "Retorna o centro de custo correspondente ao id informado.")
    @ApiResponse(responseCode = "200", description = "Centro de custo encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CentroDeCustoResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Centro de custo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping(value = "/obter/{id}")
    public ResponseEntity<CentroDeCustoResponseDto> buscarPorId(
            @Parameter(description = "Id do centro de custo") @PathVariable long id) {
        return ResponseEntity.ok(centroDeCustoService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar centro de custo", description = "Cria um novo centro de custo.")
    @ApiResponse(responseCode = "201", description = "Centro de custo criado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CentroDeCustoResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<CentroDeCustoResponseDto> criar(
            @Parameter(description = "Dados do centro de custo") @RequestBody CentroDeCustoRequestDto dto) {
        CentroDeCustoResponseDto usuario = centroDeCustoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Operation(summary = "Atualizar centro de custo", description = "Atualiza os dados do centro de custo pelo id.")
    @ApiResponse(responseCode = "200", description = "Centro de custo atualizado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CentroDeCustoResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Centro de custo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<CentroDeCustoResponseDto> atualizar(
            @Parameter(description = "Id do centro de custo") @PathVariable long id,
            @Parameter(description = "Dados do centro de custo") @RequestBody CentroDeCustoRequestDto dto) {
        return ResponseEntity.ok(centroDeCustoService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir centro de custo", description = "Remove o centro de custo pelo id.")
    @ApiResponse(responseCode = "204", description = "Centro de custo removido")
    @ApiResponse(responseCode = "404", description = "Centro de custo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Id do centro de custo") @PathVariable long id) {
        centroDeCustoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
