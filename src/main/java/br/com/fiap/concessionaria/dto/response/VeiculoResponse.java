package br.com.fiap.concessionaria.dto.response;

import java.time.Year;

public record VeiculoResponse(
        String modelo,
        Year anoDeFabricacao,
        TipoVeiculoResponse tipo,
        String cor,
        FabricanteResponse fabricante,
        Double preco,
        String nome,
        String palavraDeEfeito,
        Short cilindradas,
        Long id
) {
}
