package br.com.seguradora.seguradora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.seguradora.seguradora.model.Apolice;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, Long>{
	public Apolice findByNumero(Long numero);
}
