package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class CaracteristicaService implements ServiceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse>{
    @Autowired
    CaracteristicaRepository caracteristicaRepository;
    @Autowired
    VeiculoService veiculoService;

    @Override
    public Caracteristica toEntity(CaracteristicaRequest dto) {

        var veiculo = veiculoService.toEntity(dto.veiculo());

        return Caracteristica.builder()
                .nome(dto.nome())
                .veiculo(veiculo)
                .descricao(dto.descricao())
                .build();
    }

    @Override
    public CaracteristicaResponse toResponse(Caracteristica e) {

        if (Objects.isNull(e)) return null;

        var veiculo = veiculoService.toResponse(e.getVeiculo());

        return CaracteristicaResponse.builder()
                .id(e.getId())
                .veiculo(veiculo)
                .nome(e.getNome())
                .descricao(e.getDescricao())
                .build();
    }

    @Override
    public Collection<Caracteristica> findAll(Example<Caracteristica> example) {
        return caracteristicaRepository.findAll();
    }

    @Override
    public Caracteristica findById(Long id) {
        return caracteristicaRepository.findById(id).orElse(null);
    }

    @Override
    public Caracteristica save(Caracteristica e) {
        return caracteristicaRepository.save(e);
    }
}