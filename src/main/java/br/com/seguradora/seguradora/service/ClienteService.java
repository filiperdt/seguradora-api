package br.com.seguradora.seguradora.service;

import org.springframework.http.ResponseEntity;

import br.com.seguradora.seguradora.dto.request.ClienteRequestDto;

public interface ClienteService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(ClienteRequestDto clienteDto);

	public ResponseEntity<?> read(String cpf);

	public ResponseEntity<?> update(String cpf, ClienteRequestDto cliente);
	
	public ResponseEntity<?> delete(String cpf);
}
