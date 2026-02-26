package com.marcelopinotti.radar_financeiro.domain.repository;

import com.marcelopinotti.radar_financeiro.domain.model.CentroDeCusto;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long> {

    List<CentroDeCusto> findByUsuario(Usuario usuario);
}
