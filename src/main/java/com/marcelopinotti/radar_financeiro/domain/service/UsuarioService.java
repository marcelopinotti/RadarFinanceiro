package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.exception.ResourceNotFoundException;
import com.marcelopinotti.radar_financeiro.domain.exception.ResourceBadRequestException;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.UsuarioRepository;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService implements CRUDService<UsuarioRequestDTO, UsuarioResponseDTO> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = toEntity(dto);
        validarCadastro(usuario);
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.email());
        if (optUsuario.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário cadastrado com o email: " + dto.email());
        }
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(new Date());
        }
        return toResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(long id) {
        return usuarioRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    @Override
    public UsuarioResponseDTO atualizar(long id, UsuarioRequestDTO dto) {
        Usuario usuarioBanco = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        String senha = passwordEncoder.encode(dto.senha());
        usuarioBanco.setSenha(senha);
        usuarioBanco.setNome(dto.nome());
        usuarioBanco.setEmail(dto.email().toLowerCase());
        usuarioBanco.setCelular(dto.celular());

        validarCadastro(usuarioBanco);
        return toResponseDTO(usuarioRepository.save(usuarioBanco));
    }

    @Override
    public void deletar(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuario.setDataInativacao(new Date());
        usuarioRepository.save(usuario);
    }

    private void validarCadastro(Usuario usuario) {
        List<String> faltantes = new ArrayList<>();
        if (usuario.getNome() == null) faltantes.add("nome");
        if (usuario.getEmail() == null) faltantes.add("email");
        if (usuario.getSenha() == null) faltantes.add("senha");
        if (usuario.getCelular() == null) faltantes.add("celular");
        if (!faltantes.isEmpty()) {
            throw new ResourceBadRequestException("Campos obrigatórios ausentes: " + String.join(", ", faltantes));
        }

        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
    }

    // Métodos de conversão (substitui o ModelMapper)
    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setCelular(dto.celular());
        return usuario;
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getCelular(),
            usuario.getDataCadastro(),
            usuario.getDataInativacao()
        );
    }
}
