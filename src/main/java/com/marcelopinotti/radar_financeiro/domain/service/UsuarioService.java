package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.exception.ResourceNotFoundException;
import com.marcelopinotti.radar_financeiro.domain.exception.ResourceBadRequestException;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.UsuarioRepository;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private ModelMapper mapper;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = mapper.map(dto, Usuario.class);
        validarCadastro(usuario);
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        if (optUsuario.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário cadastrado com o email: " + dto.getEmail());
        }
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(new Date());
        }
        return mapper.map(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
    }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    @Override
    public UsuarioResponseDTO atualizar(long id, UsuarioRequestDTO dto) {
        Usuario usuarioBanco = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        usuarioBanco.setNome(dto.getNome());
        usuarioBanco.setEmail(dto.getEmail().toLowerCase());
        usuarioBanco.setSenha(dto.getSenha());
        usuarioBanco.setCelular(dto.getCelular());

        validarCadastro(usuarioBanco);
        return mapper.map(usuarioRepository.save(usuarioBanco), UsuarioResponseDTO.class);
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

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));


    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
    }
}
