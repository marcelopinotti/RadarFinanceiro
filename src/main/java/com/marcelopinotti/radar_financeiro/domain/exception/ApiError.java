package com.marcelopinotti.radar_financeiro.domain.exception;

public record ApiError(
    String timestamp,
    int status,
    String erro,
    String mensagem,
    String path
) {}
