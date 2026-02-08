package com.marcelopinotti.radar_financeiro.domain.repository;

import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
