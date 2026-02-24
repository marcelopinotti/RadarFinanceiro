package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.model.CentroDeCusto;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.CentroDeCustoRepository;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoRequestDto;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroDeCustoService implements CRUDService<CentroDeCustoRequestDto, CentroDeCustoResponseDto> {

    @Autowired
    private CentroDeCustoRepository centroDeCustoRepository;

    @Override
    public CentroDeCustoResponseDto criar(CentroDeCustoRequestDto dto) {
        CentroDeCusto centroDeCusto = toEntity(dto);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        return toResponseDTO(centroDeCustoRepository.save(centroDeCusto));
    }

    @Override
    public List<CentroDeCustoResponseDto> obterTodos() {
        List<CentroDeCusto> lista = centroDeCustoRepository.findAll();
        return lista.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CentroDeCustoResponseDto buscarPorId(long id) {
        return centroDeCustoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Centro de custo não encontrado com id: " + id));
    }

    @Override
    public CentroDeCustoResponseDto atualizar(long id, CentroDeCustoRequestDto dto) {
        buscarPorId(id);
        CentroDeCusto centroDeCusto = toEntity(dto);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);

        centroDeCusto.setId(id);
        return toResponseDTO(centroDeCustoRepository.save(centroDeCusto));

    }

    @Override
    public void deletar(long id) {
    buscarPorId(id);
    centroDeCustoRepository.deleteById(id);
    }

    private CentroDeCusto toEntity(CentroDeCustoRequestDto dto) {
        CentroDeCusto centroDeCusto = new CentroDeCusto();
        centroDeCusto.setDescricao(dto.descricao());
        centroDeCusto.setObservacao(dto.observacao());
        return centroDeCusto;
    }

    private CentroDeCustoResponseDto toResponseDTO(CentroDeCusto centroDeCusto) {
        return new CentroDeCustoResponseDto(
                centroDeCusto.getId(),
                centroDeCusto.getDescricao(),
                centroDeCusto.getObservacao()
        );
    }
}
