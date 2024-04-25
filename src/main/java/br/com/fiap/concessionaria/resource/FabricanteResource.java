package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.FabricanteRequest;
import br.com.fiap.concessionaria.dto.response.FabricanteResponse;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.service.FabricanteService;
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
@RequestMapping(value = "/fabricantes")
public class FabricanteResource implements ResourceDTO<Fabricante, FabricanteRequest, FabricanteResponse> {
    @Autowired
    private FabricanteService fabricanteService;

    @GetMapping
    public ResponseEntity<Collection<FabricanteResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nomeFantasia", required = false) String nomeFantasia

    ) {

        var fabricante = Fabricante.builder()
                .nome(nome)
                .nomeFantasia(nomeFantasia)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Fabricante> example = Example.of(fabricante, matcher);
        Collection<Fabricante> fabricantes = fabricanteService.findAll(example);

        var response = fabricantes.stream().map(fabricanteService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<FabricanteResponse> save(@RequestBody @Valid FabricanteRequest r) {
        var entity = fabricanteService.toEntity(r);
        var saved = fabricanteService.save(entity);
        var response = fabricanteService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<FabricanteResponse> findById(@PathVariable Long id) {
        var entity = fabricanteService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = fabricanteService.toResponse(entity);
        return ResponseEntity.ok(response);
    }
}