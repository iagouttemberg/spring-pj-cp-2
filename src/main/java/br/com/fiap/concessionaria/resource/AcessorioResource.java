package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.service.AcessorioService;
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
@RequestMapping(value = "/acessorio")
public class AcessorioResource implements ResourceDTO<Acessorio, AcessorioRequest, AcessorioResponse>{
    @Autowired
    private AcessorioService acessorioService;

    @GetMapping
    public ResponseEntity<Collection<AcessorioResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) Double preco

    ) {

        var acessorio = Acessorio.builder()
                .nome(nome)
                .preco(preco)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Acessorio> example = Example.of(acessorio, matcher);
        Collection<Acessorio> acessorios = acessorioService.findAll(example);

        var response = acessorios.stream().map(acessorioService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<AcessorioResponse> save(@RequestBody @Valid AcessorioRequest r) {
        var entity = acessorioService.toEntity(r);
        var saved = acessorioService.save(entity);
        var response = acessorioService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<AcessorioResponse> findById(@PathVariable Long id) {
        var entity = acessorioService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = acessorioService.toResponse(entity);
        return ResponseEntity.ok(response);
    }
}