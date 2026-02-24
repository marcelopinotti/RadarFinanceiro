package com.marcelopinotti.radar_financeiro.dto.usuario;

import java.util.Date;

public record UsuarioResponseDTO(
    long id,
    String nome,
    String email,
    String celular,
    Date dataCadastro,
    Date dataInativacao
) {}
