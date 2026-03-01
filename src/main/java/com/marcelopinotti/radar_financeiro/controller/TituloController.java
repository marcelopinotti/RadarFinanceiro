package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.TituloService;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloRequestDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
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
@RequestMapping("/api/titulo")
@Tag(name = "Titulos", description = "Operacoes relacionadas a titulos")
public class TituloController {


    private final TituloService tituloService;

    @Operation(summary = "Listar titulos", description = "Retorna todos os titulos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TituloResponseDto.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping(value = "/obter")
    public ResponseEntity<List<TituloResponseDto>> obterTodos() {
        return ResponseEntity.ok(tituloService.obterTodos());
    }

    @Operation(summary = "Buscar titulo por id", description = "Retorna o titulo correspondente ao id informado.")
    @ApiResponse(responseCode = "200", description = "Titulo encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TituloResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Titulo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping(value = "/obter/{id}")
    public ResponseEntity<TituloResponseDto> buscarPorId(
            @Parameter(description = "Id do titulo") @PathVariable long id) {
        return ResponseEntity.ok(tituloService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar titulo", description = "Cria um novo titulo.")
    @ApiResponse(responseCode = "201", description = "Titulo criado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TituloResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<TituloResponseDto> criar(
            @Parameter(description = "Dados do titulo") @RequestBody TituloRequestDto dto) {
        TituloResponseDto titulo = tituloService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(titulo);
    }

    @Operation(summary = "Atualizar titulo", description = "Atualiza os dados do titulo pelo id.")
    @ApiResponse(responseCode = "200", description = "Titulo atualizado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TituloResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Titulo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<TituloResponseDto> atualizar(
            @Parameter(description = "Id do titulo") @PathVariable long id,
            @Parameter(description = "Dados do titulo") @RequestBody TituloRequestDto dto) {
        return ResponseEntity.ok(tituloService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir titulo", description = "Remove o titulo pelo id.")
    @ApiResponse(responseCode = "204", description = "Titulo removido")
    @ApiResponse(responseCode = "404", description = "Titulo nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Id do titulo") @PathVariable long id) {
        tituloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
