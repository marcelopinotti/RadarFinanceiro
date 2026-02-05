package com.marcelopinotti.radar_financeiro.domain.repository;

import com.marcelopinotti.radar_financeiro.domain.model.CentroDeCusto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentroDeCustoRepository  extends JpaRepository<CentroDeCusto, Long> {
}
