package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.TituloService;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloRequestDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
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
@RequestMapping("/api/titulo")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @GetMapping(value = "/obter")
    public ResponseEntity<List<TituloResponseDto>> obterTodos() {
        return ResponseEntity.ok(tituloService.obterTodos());
    }

    @GetMapping(value = "/obter/{id}")
    public ResponseEntity<TituloResponseDto> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(tituloService.buscarPorId(id));
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<TituloResponseDto> criar(@RequestBody TituloRequestDto dto) {
        TituloResponseDto titulo = tituloService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(titulo);
    }

    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<TituloResponseDto> atualizar(@PathVariable long id, @RequestBody TituloRequestDto dto) {
        return ResponseEntity.ok(tituloService.atualizar(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        tituloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
