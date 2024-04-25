package br.com.fiap.concessionaria.repository;

import br.com.fiap.concessionaria.entity.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {
}