package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.exception.ResourceBadRequestException;
import com.marcelopinotti.radar_financeiro.domain.model.CentroDeCusto;
import com.marcelopinotti.radar_financeiro.domain.model.Titulo;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.TituloRepository;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloRequestDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TituloService implements CRUDService<TituloRequestDto, TituloResponseDto> {

    @Autowired
    private TituloRepository tituloRepository;

    @Override
    public List<TituloResponseDto> obterTodos() {
        List<Titulo> titulos = tituloRepository.findAll();
        return titulos.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public TituloResponseDto buscarPorId(long id) {
        return tituloRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Título não encontrado com id: " + id));
    }

    @Override
    public TituloResponseDto criar(TituloRequestDto dto) {
        validarTitulo(dto);
        Titulo titulo = toEntity(dto);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        return toResponseDTO(tituloRepository.save(titulo));
    }

    @Override
    public TituloResponseDto atualizar(long id, TituloRequestDto dto) {
        buscarPorId(id);
        Titulo titulo = toEntity(dto);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        titulo.setUsuario(usuario);
        titulo.setId(id);
        return toResponseDTO(tituloRepository.save(titulo));
    }

    @Override
    public void deletar(long id) {
        buscarPorId(id);
        tituloRepository.deleteById(id);
    }

    private Titulo toEntity(TituloRequestDto dto) {
        Titulo titulo = new Titulo();
        titulo.setDescricao(dto.descricao());
        titulo.setValor(dto.valor());
        titulo.setDataVencimento(dto.dataVencimento());
        titulo.setDataPagamento(dto.dataPagamento());
        titulo.setTipo(dto.tipo());
        return titulo;
    }

    private void validarTitulo(TituloRequestDto dto) {
        if (dto.descricao() == null || dto.valor() == null || dto.dataVencimento() == null || dto.tipo() == null) {
            throw new ResourceBadRequestException("Os campos descrição,valor,data de vencimento,tipo são obrigatórios");
        }
    }
    private TituloResponseDto toResponseDTO(Titulo titulo) {
        return new TituloResponseDto(
                titulo.getId(),
                titulo.getDescricao(),
                titulo.getValor(),
                titulo.getDataVencimento(),
                titulo.getDataPagamento(),
                titulo.getTipo(),
                titulo.getCentroDeCusto() != null
                    ? titulo.getCentroDeCusto().stream()
                        .map(cc -> new CentroDeCustoResponseDto(cc.getId(), cc.getDescricao(), cc.getObservacao()))
                        .toList()
                    : null
        );
    }
}
