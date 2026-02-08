package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.exception.ResourceNotFoundException;
import com.marcelopinotti.radar_financeiro.domain.exception.ResourceBadRequestException;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.UsuarioRepository;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements CRUDService<UsuarioRequestDTO, UsuarioResponseDTO> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = mapper.map(dto, Usuario.class);
        validarCadastro(usuario);
        return mapper.map(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
    }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO buscarPorId(long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    @Override
    public UsuarioResponseDTO atualizar(long id, UsuarioRequestDTO dto) {
        buscarPorId(id);
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(id);
        return mapper.map(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
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

        String emailNormalizado = usuario.getEmail().toLowerCase();
        boolean emailJaExiste = !usuarioRepository.findByEmail(emailNormalizado).isEmpty();
        if (emailJaExiste) {
            throw new ResourceBadRequestException("Email já cadastrado");
        }
//        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));


    }
}
