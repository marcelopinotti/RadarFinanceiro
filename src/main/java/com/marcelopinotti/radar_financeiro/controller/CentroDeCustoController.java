package com.marcelopinotti.radar_financeiro.controller;

import com.marcelopinotti.radar_financeiro.domain.service.CentroDeCustoService;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoRequestDto;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;
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
public class CentroDeCustoController {


    private final CentroDeCustoService centroDeCustoService;

    @GetMapping(value = "/obter")
    public ResponseEntity<List<CentroDeCustoResponseDto>> obterTodos() {
        return ResponseEntity.ok(centroDeCustoService.obterTodos());
    }
    @GetMapping(value = "/obter/{id}")
    public ResponseEntity<CentroDeCustoResponseDto> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(centroDeCustoService.buscarPorId(id));
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<CentroDeCustoResponseDto> criar(@RequestBody CentroDeCustoRequestDto dto) {
        CentroDeCustoResponseDto usuario = centroDeCustoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PatchMapping(value = "/atualizar/{id}", consumes = "application/json")
    public ResponseEntity<CentroDeCustoResponseDto> atualizar(@PathVariable long id, @RequestBody CentroDeCustoRequestDto dto) {
        return ResponseEntity.ok(centroDeCustoService.atualizar(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        centroDeCustoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
