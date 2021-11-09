package br.com.seguradora.seguradora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.seguradora.seguradora.model.Apolice;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, Long>{
	public Optional<Apolice> findByNumero(Long numero);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM seguradora.apolice where cliente_cpf = ?1")
	public List<Apolice> encontrarPorCliente(String cpf);
	
	public void deleteByNumero(Long numero);
}
