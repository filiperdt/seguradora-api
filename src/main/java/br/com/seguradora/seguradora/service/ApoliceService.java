package br.com.seguradora.seguradora.service;

import org.springframework.http.ResponseEntity;

import br.com.seguradora.seguradora.dto.request.ApoliceRequestDto;

public interface ApoliceService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(ApoliceRequestDto pedidoDto);

	public ResponseEntity<?> read(Long numero);

	public ResponseEntity<?> update(Long numero, ApoliceRequestDto pedido);
	
	public ResponseEntity<?> delete(Long numero);
}
