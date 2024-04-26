package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.*;
import br.com.fiap.concessionaria.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class VeiculoService implements ServiceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{
    @Autowired
    VeiculoRepository veiculoRepository;
    @Autowired
    FabricanteService fabricanteService;
    @Autowired
    TipoVeiculoService tipoVeiculoService;

    @Override
    public Veiculo toEntity(VeiculoRequest dto) {

        var fabricante = fabricanteService.findById(dto.fabricante().id());
        var tipo = tipoVeiculoService.findById(dto.tipo().id());

        return Veiculo.builder()
                .preco(dto.preco())
                .nome(dto.nome())
                .fabricante(fabricante)
                .modelo(dto.modelo())
                .anoDeFabricacao(dto.anoDeFabricacao())
                .tipo(tipo)
                .cor(dto.cor())
                .palavraDeEfeito(dto.palavraDeEfeito())
                .cilindradas(dto.cilindradas())
                .build();
    }

    @Override
    public VeiculoResponse toResponse(Veiculo e) {

        if (Objects.isNull(e)) return null;

        var fabricante = fabricanteService.toResponse(e.getFabricante());
        var tipo = tipoVeiculoService.toResponse(e.getTipo());

        return VeiculoResponse.builder()
                .preco(e.getPreco())
                .nome(e.getNome())
                .fabricante(fabricante)
                .modelo(e.getModelo())
                .anoDeFabricacao(e.getAnoDeFabricacao())
                .tipo(tipo)
                .cor(e.getCor())
                .palavraDeEfeito(e.getPalavraDeEfeito())
                .id(e.getId())
                .cilindradas(e.getCilindradas())
                .build();
    }

    @Override
    public Collection<Veiculo> findAll(Example<Veiculo> example) {
        return veiculoRepository.findAll(example);
    }

    @Override
    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id).orElse(null);
    }

    @Override
    public Veiculo save(Veiculo e) {
        return veiculoRepository.save(e);
    }
}