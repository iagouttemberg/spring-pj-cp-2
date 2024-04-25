package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.service.LojaService;
import br.com.fiap.concessionaria.service.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/loja")
public class LojaResource implements ResourceDTO<Loja, LojaRequest, LojaResponse>{
    @Autowired
    private LojaService lojaService;
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<Collection<LojaResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome

    ) {

        var loja = Loja.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Loja> example = Example.of(loja, matcher);
        Collection<Loja> lojas = lojaService.findAll(example);

        var response = lojas.stream().map(lojaService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest r) {
        var entity = lojaService.toEntity(r);
        var saved = lojaService.save(entity);
        var response = lojaService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<LojaResponse> findById(@PathVariable Long id) {
        var entity = lojaService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = lojaService.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/veiculos")
    public ResponseEntity<VeiculoResponse> findVeiculoByLoja(@PathVariable Long id) {
        var veiculo = veiculoService.findById(id);
        var response = veiculoService.toResponse(veiculo);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping(value = "/{id}/veiculos")
    public ResponseEntity<VeiculoResponse> save(@PathVariable Long id, @RequestBody @Valid VeiculoRequest veiculo) {

        var loja = lojaService.findById(id);

        if (Objects.isNull(veiculo)) return ResponseEntity.badRequest().build();

        var entity = veiculoService.toEntity(veiculo);

        loja.getVeiculosComercializados().add(entity);

        var saved = veiculoService.save(entity);

        var response = veiculoService.toResponse(saved);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}