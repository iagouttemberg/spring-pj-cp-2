package br.com.fiap.concessionaria.dto.response;

import lombok.Builder;

@Builder
public record AcessorioResponse(
        String nome,
        Double preco,
        Long id
) {
}