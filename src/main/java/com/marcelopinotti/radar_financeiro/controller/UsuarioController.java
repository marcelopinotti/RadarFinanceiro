package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.UsuarioService;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operacoes relacionadas a usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios", description = "Retorna todos os usuarios cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping("/obter")
    public ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        return ResponseEntity.ok(usuarioService.obterTodos());
    }

    @Operation(summary = "Buscar usuario por id", description = "Retorna o usuario correspondente ao id informado.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping("/obter/{id}")
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@Parameter(description = "Id do usuario") @PathVariable long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar usuario", description = "Cria um novo usuario.")
    @ApiResponse(responseCode = "201", description = "Usuario criado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criar(
            @Parameter(description = "Dados do usuario") @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuario);
    }

    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados do usuario pelo id.")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @Parameter(description = "Id do usuario") @PathVariable long id,
            @Parameter(description = "Dados do usuario") @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir usuario", description = "Remove o usuario pelo id.")
    @ApiResponse(responseCode = "204", description = "Usuario removido")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@Parameter(description = "Id do usuario") @PathVariable long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
