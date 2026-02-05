package com.marcelopinotti.radar_financeiro.domain.service;


import java.util.List;

public interface CRUDService<Request, Response, Id> {

    Response criar(Request dto);

    List<Request> obterTodos();

    Response buscarPorId(Id id);

    Response atualizar(Id id, Request dto);

    void deletar(Id id);

}
