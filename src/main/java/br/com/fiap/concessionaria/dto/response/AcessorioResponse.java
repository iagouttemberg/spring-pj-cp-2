package br.com.fiap.concessionaria.dto.response;

public record AcessorioResponse(
        String nome,
        Double preco,
        Long id
) {
}
