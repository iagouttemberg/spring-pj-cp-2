package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.FabricanteRequest;
import br.com.fiap.concessionaria.dto.response.FabricanteResponse;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class FabricanteService implements ServiceDTO<Fabricante, FabricanteRequest, FabricanteResponse>{
    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Override
    public Fabricante toEntity(FabricanteRequest dto) {

        return Fabricante.builder()
                .nome(dto.nome())
                .nomeFantasia(dto.nomeFantasia())
                .build();
    }

    @Override
    public FabricanteResponse toResponse(Fabricante e) {

        if (Objects.isNull(e)) return null;

        return FabricanteResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .nomeFantasia(e.getNomeFantasia())
                .build();
    }

    @Override
    public Collection<Fabricante> findAll(Example<Fabricante> example) {
        return fabricanteRepository.findAll(example);
    }

    @Override
    public Fabricante findById(Long id) {
        return fabricanteRepository.findById(id).orElse(null);
    }

    @Override
    public Fabricante save(Fabricante e) {
        return fabricanteRepository.save(e);
    }
}