package br.com.fiap.concessionaria.dto.response;

import lombok.Builder;

@Builder
public record TipoVeiculoResponse(
        Long id,
        String nome
) {
}