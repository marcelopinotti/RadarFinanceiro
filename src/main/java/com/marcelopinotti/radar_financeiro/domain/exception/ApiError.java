package com.marcelopinotti.radar_financeiro.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiError {
    private String timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String path;
}
