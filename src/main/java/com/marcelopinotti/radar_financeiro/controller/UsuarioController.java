package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.UsuarioService;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/obter")
    public ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        return ResponseEntity.ok(usuarioService.obterTodos());
    }
    @GetMapping("/obter/{id}")
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.criar(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(usuario);
    }
    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable long id, @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
