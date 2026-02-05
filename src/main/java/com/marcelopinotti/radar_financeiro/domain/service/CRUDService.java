package com.marcelopinotti.radar_financeiro.domain.service;


import java.util.List;

public interface CRUDService<Request, Response> {

    Response criar(Request dto);

    List<Response> obterTodos();

    Response buscarPorId(long id);

    Response atualizar(long id, Request dto);

    void deletar(long id);

}
