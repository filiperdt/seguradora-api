package br.com.seguradora.seguradora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.seguradora.seguradora.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{
	public Cliente findByCpf(String cpf);
}
