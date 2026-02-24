package com.marcelopinotti.radar_financeiro.dto.usuario;

public record UsuarioRequestDTO(
    String nome,
    String email,
    String senha,
    String celular
) {}
