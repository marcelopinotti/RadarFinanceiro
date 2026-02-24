package com.marcelopinotti.radar_financeiro.dto.usuario;

public record LoginResponseDTO(
    String token,
    UsuarioResponseDTO usuario
) {}
