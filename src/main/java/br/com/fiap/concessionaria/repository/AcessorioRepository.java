package br.com.fiap.concessionaria.repository;

import br.com.fiap.concessionaria.entity.Acessorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessorioRepository extends JpaRepository<Acessorio, Long> {
}