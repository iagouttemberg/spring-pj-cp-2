package br.com.fiap.concessionaria.resource;

import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.service.CaracteristicaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Year;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/caracteristicas")
public class CaracteristicaResource implements ResourceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse> {
    @Autowired
    private CaracteristicaService caracteristicaService;

    @GetMapping
    public ResponseEntity<Collection<CaracteristicaResponse>> findAll(

            @RequestParam(name = "veiculo.preco", required = false) Double veiculoPreco,
            @RequestParam(name = "veiculo.modelo", required = false) String veiculoModelo,
            @RequestParam(name = "veiculo.anoDeFabricacao", required = false) Year veiculoAnoDeFabricacao,
            @RequestParam(name = "veiculo.nome", required = false) String veiculoNome,
            @RequestParam(name = "veiculo.tipo", required = false) TipoVeiculo veiculoTipo,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao

    ) {

        var veiculo = Veiculo.builder()
                .preco(veiculoPreco)
                .modelo(veiculoModelo)
                .anoDeFabricacao(veiculoAnoDeFabricacao)
                .nome(veiculoNome)
                .tipo(veiculoTipo)
                .build();

        var caracteristica = Caracteristica.builder()
                .veiculo(veiculo)
                .nome(nome)
                .descricao(descricao)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Caracteristica> example = Example.of(caracteristica, matcher);
        Collection<Caracteristica> caracteristicas = caracteristicaService.findAll(example);

        var response = caracteristicas.stream().map(caracteristicaService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<CaracteristicaResponse> save(@RequestBody @Valid CaracteristicaRequest r) {
        var entity = caracteristicaService.toEntity(r);
        var saved = caracteristicaService.save(entity);
        var response = caracteristicaService.toResponse(saved);


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<CaracteristicaResponse> findById(@PathVariable Long id) {
        var entity = caracteristicaService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = caracteristicaService.toResponse(entity);
        return ResponseEntity.ok(response);
    }
}