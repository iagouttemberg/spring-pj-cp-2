package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.TipoVeiculoRequest;
import br.com.fiap.concessionaria.dto.response.TipoVeiculoResponse;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.service.TipoVeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/tipo-de-veiculo")
public class TipoVeiculoResource implements ResourceDTO<TipoVeiculo, TipoVeiculoRequest, TipoVeiculoResponse>{
    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @GetMapping
    public ResponseEntity<Collection<TipoVeiculoResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome

    ) {

        var tipoVeiculo = TipoVeiculo.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<TipoVeiculo> example = Example.of(tipoVeiculo, matcher);
        Collection<TipoVeiculo> tiposVeiculo = tipoVeiculoService.findAll(example);

        var response = tiposVeiculo.stream().map(tipoVeiculoService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<TipoVeiculoResponse> save(@RequestBody @Valid TipoVeiculoRequest r) {
        var entity = tipoVeiculoService.toEntity(r);
        var saved = tipoVeiculoService.save(entity);
        var response = tipoVeiculoService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<TipoVeiculoResponse> findById(@PathVariable Long id) {
        var entity = tipoVeiculoService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = tipoVeiculoService.toResponse(entity);
        return ResponseEntity.ok(response);
    }
}