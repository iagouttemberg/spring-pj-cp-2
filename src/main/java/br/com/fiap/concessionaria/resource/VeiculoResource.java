package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.service.AcessorioService;
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
import java.time.Year;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/veiculo")
public class VeiculoResource implements ResourceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private AcessorioService acessorioService;

    @GetMapping
    public ResponseEntity<Collection<VeiculoResponse>> findAll(

            @RequestParam(name = "tipo.nome", required = false) String veiculoTipo,
            @RequestParam(name = "farbicante.nome", required = false) String fabricanteNome,
            @RequestParam(name = "fabricante.nomeFantasia", required = false) String fabricanteNomeFantasia,
            @RequestParam(name = "modelo", required = false) String modelo,
            @RequestParam(name = "anoDeFabricacao", required = false) Year anoDeFabricacao,
            @RequestParam(name = "cor", required = false) String cor,
            @RequestParam(name = "preco", required = false) Double preco,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "palavraDeEfeito", required = false) String palavraDeEfeito,
            @RequestParam(name = "cilindradas", required = false) Short cilindradas

    ) {

        var tipoVeiculo = TipoVeiculo.builder()
                .nome(veiculoTipo)
                .build();

        var fabricante = Fabricante.builder()
                .nome(fabricanteNome)
                .nomeFantasia(fabricanteNomeFantasia)
                .build();

        var veiculo = Veiculo.builder()
                .tipo(tipoVeiculo)
                .fabricante(fabricante)
                .modelo(modelo)
                .anoDeFabricacao(anoDeFabricacao)
                .cor(cor)
                .preco(preco)
                .nome(nome)
                .palavraDeEfeito(palavraDeEfeito)
                .cilindradas(cilindradas)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Veiculo> example = Example.of(veiculo, matcher);
        Collection<Veiculo> veiculos = veiculoService.findAll(example);

        var response = veiculos.stream().map(veiculoService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<VeiculoResponse> save(@RequestBody @Valid VeiculoRequest r) {
        var entity = veiculoService.toEntity(r);
        var saved = veiculoService.save(entity);
        var response = veiculoService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<VeiculoResponse> findById(@PathVariable Long id) {
        var entity = veiculoService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = veiculoService.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/acessorios")
    public ResponseEntity<AcessorioResponse> findAcessorioByVeiculo(@PathVariable Long id) {
        var acessorio = acessorioService.findById(id);
        var response = acessorioService.toResponse(acessorio);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping(value = "/{id}/acessorios")
    public ResponseEntity<AcessorioResponse> save(@PathVariable Long id, @RequestBody @Valid AcessorioRequest acessorio) {

        var veiculo = veiculoService.findById(id);

        if (Objects.isNull(acessorio)) return ResponseEntity.badRequest().build();

        var entity = acessorioService.toEntity(acessorio);

        veiculo.getAcessorios().add(entity);

        var saved = acessorioService.save(entity);

        var response = acessorioService.toResponse(saved);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}