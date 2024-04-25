package br.com.fiap.concessionaria.dto.response;

import java.util.Set;

public record LojaResponse(
        String nome,
        Set<VeiculoResponse> veiculosComercializados,
        Long id
) {
}
