package com.marcelopinotti.radar_financeiro.domain.repository;

import com.marcelopinotti.radar_financeiro.domain.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
}
