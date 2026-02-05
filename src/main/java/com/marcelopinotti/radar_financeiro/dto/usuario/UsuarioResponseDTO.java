package com.marcelopinotti.radar_financeiro.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private long id;
    private String nome;
    private String email;
    private String celular;
    private Date dataInativacao;


}
