package com.marcelopinotti.radar_financeiro.domain.repository;

import com.marcelopinotti.radar_financeiro.domain.model.Titulo;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo, Long> {

    @Query("""
       SELECT t
       FROM Titulo t
       WHERE t.dataVencimento BETWEEN :periodoInicial AND :periodoFinal
       """)
    List<Titulo> obterPorDataVencimento(
            @Param("periodoInicial") Date periodoInicial,
            @Param("periodoFinal") Date periodoFinal
    );

    List<Titulo> findByUsuario(Usuario usuario);
}
