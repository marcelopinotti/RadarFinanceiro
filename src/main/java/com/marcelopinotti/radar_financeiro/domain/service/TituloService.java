package com.marcelopinotti.radar_financeiro.domain.service;

import com.marcelopinotti.radar_financeiro.domain.exception.ResourceBadRequestException;
import com.marcelopinotti.radar_financeiro.domain.model.Titulo;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.repository.CentroDeCustoRepository;
import com.marcelopinotti.radar_financeiro.domain.repository.TituloRepository;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoRequestDto;
import com.marcelopinotti.radar_financeiro.dto.centro_de_custo.CentroDeCustoResponseDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloRequestDto;
import com.marcelopinotti.radar_financeiro.dto.titulo.TituloResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TituloService implements CRUDService<TituloRequestDto, TituloResponseDto> {

    private final TituloRepository tituloRepository;

    private final CentroDeCustoRepository centroDeCustoRepository;

    @Override
    public List<TituloResponseDto> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Titulo> titulos = tituloRepository.findByUsuario(usuario);
        return titulos.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public TituloResponseDto buscarPorId(long id) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return tituloRepository.findByUsuario(usuario)
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Título não encontrado com id: " + id));
    }
@Override
    public TituloResponseDto criar(TituloRequestDto dto) {
        validarTitulo(dto);

        Titulo titulo = toEntity(dto);

        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        titulo.setUsuario(usuario);

        if (dto.centroDeCusto() != null && !dto.centroDeCusto().isEmpty()) {
            var ccIds = dto.centroDeCusto().stream()
                    .map(CentroDeCustoRequestDto::id)
                    .toList();
            var centros = centroDeCustoRepository.findAllById(ccIds);
            titulo.setCentroDeCusto(centros);
        } else {titulo.setCentroDeCusto(new ArrayList<>());}

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

    public List<TituloResponseDto> obterPorDataVencimento(Date periodoInicial, Date periodoFinal) {
        List<Titulo> titulos = tituloRepository.obterPorDataVencimento(periodoInicial, periodoFinal);
        return titulos.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
