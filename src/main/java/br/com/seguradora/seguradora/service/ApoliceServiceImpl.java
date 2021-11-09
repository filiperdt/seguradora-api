package br.com.seguradora.seguradora.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.seguradora.seguradora.dto.request.ApoliceRequestDto;
import br.com.seguradora.seguradora.dto.response.ApoliceResponseDto;
import br.com.seguradora.seguradora.dto.response.ClienteResponseDto;
import br.com.seguradora.seguradora.model.Apolice;
import br.com.seguradora.seguradora.model.Cliente;
import br.com.seguradora.seguradora.repository.ApoliceRepository;
import br.com.seguradora.seguradora.repository.ClienteRepository;
import net.minidev.json.JSONObject;

@Service
public class ApoliceServiceImpl implements ApoliceService {
	@Autowired
	private ApoliceRepository apoliceRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ClienteServiceImpl clienteServiceImpl;
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Apolice> apolices = apoliceRepository.findAll();
		List<ApoliceResponseDto> apolicesDto = new ArrayList<>();
		
		apolices.stream().forEach(apolice -> {
			Optional<Cliente> optional = clienteRepository.findByCpf(apolice.getCliente().getCpf());
			
			if(optional.isPresent()) {
				ClienteResponseDto clienteDto = clienteServiceImpl.mapEntityParaDto(optional.get());
				ApoliceResponseDto apoliceDto = mapEntityParaDto(apolice, clienteDto);
				apolicesDto.add(apoliceDto);
			}
		});
		
		return ResponseEntity.ok().body(apolicesDto);
	}

	@Transactional
	public ResponseEntity<?> create(ApoliceRequestDto requisicao) {
		String clienteCpf = requisicao.getCliente();
		Optional<Cliente> optional = clienteRepository.findByCpf(clienteCpf);
		
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			Cliente cliente = optional.get();
			
			Apolice apolice = mapDtoParaEntity(requisicao, new Apolice(), cliente);
			
			apolice.setInicioVigencia(LocalDate.now());
			Apolice apoliceSalva = apoliceRepository.save(apolice);
			ApoliceResponseDto apoliceResponseDtoSalva = mapEntityParaDto(apoliceSalva, clienteServiceImpl.mapEntityParaDto(cliente));
			return ResponseEntity.created(null).body(apoliceResponseDtoSalva);
		}
		
		msgResposta.put("message", "Cliente CPF "+clienteCpf+" não encontrado no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> read(Long numero) {
		Optional<Apolice> optional = apoliceRepository.findByNumero(numero);
		
		if(optional.isPresent()) {
			Apolice apolice = optional.get();
			ApoliceResponseDto apoliceDto = mapEntityParaDto(apolice, clienteServiceImpl.mapEntityParaDto(apolice.getCliente()));
			return ResponseEntity.ok().body(apoliceDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Apólice #"+numero+" não encontrada no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(Long numero, ApoliceRequestDto requisicao) {
		JSONObject msgResposta = new JSONObject();

		Optional<Apolice> optionalApolice = apoliceRepository.findByNumero(numero);
		
		if(optionalApolice.isPresent()) {
			Apolice apolice = optionalApolice.get();
			String clienteCpf = requisicao.getCliente();
			
			Optional<Cliente> optionalCliente = clienteRepository.findByCpf(clienteCpf);
			
			if(optionalCliente.isPresent()) {
				Cliente clienteEntity = optionalCliente.get();
				
				Apolice apoliceEntity = mapDtoParaEntity(requisicao, apolice, clienteEntity);
				
				Apolice apoliceSalva = apoliceRepository.save(apoliceEntity);
				ApoliceResponseDto apoliceResponseDtoSalva = mapEntityParaDto(apoliceSalva, clienteServiceImpl.mapEntityParaDto(clienteEntity));
				return ResponseEntity.ok().body(apoliceResponseDtoSalva);
			}
			
			msgResposta.put("message", "Cliente CPF "+clienteCpf+" não encontrado no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
		
		msgResposta.put("message", "Apólice #"+numero+" não encontrada no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(Long numero) {
		Optional<Apolice> optional = this.apoliceRepository.findByNumero(numero);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			apoliceRepository.deleteByNumero(numero);
			msgResposta.put("message", "Apólice #"+numero+" excluída com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Apólice #"+numero+" não encontrada no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<?> consultarPorNumero(Long numero) {
		Optional<Apolice> optional = apoliceRepository.findByNumero(numero);
		
		if(optional.isPresent()) {
			Apolice apolice = optional.get();
			ApoliceResponseDto apoliceDto = mapEntityParaDto(apolice, clienteServiceImpl.mapEntityParaDto(apolice.getCliente()));
			return ResponseEntity.ok().body(apoliceDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Apólice #"+numero+" não encontrada no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}
	
	@Transactional
	public ApoliceResponseDto mapEntityParaDto(Apolice apolice) {
		ClienteResponseDto clienteDto = clienteServiceImpl.mapEntityParaDto(apolice.getCliente());
		return mapEntityParaDto(apolice, clienteDto);
	}
	
	public ApoliceResponseDto mapEntityParaDto(Apolice apolice, ClienteResponseDto clienteDto) {
		ApoliceResponseDto apoliceDto = ApoliceResponseDto.builder()
		.numero(apolice.getNumero())
		.inicioVigencia(apolice.getInicioVigencia())
		.fimVigencia(apolice.getFimVigencia())
		.placaVeiculo(apolice.getPlacaVeiculo())
		.valor(apolice.getValor().toString())
		.cliente(clienteDto)
		.build();
		
		return apoliceDto;
	}
		
	public Apolice mapDtoParaEntity(ApoliceRequestDto apoliceDto, Apolice apolice, Cliente cliente) {
		apolice.setFimVigencia(apoliceDto.getFimVigencia());
		apolice.setPlacaVeiculo(apoliceDto.getPlacaVeiculo());
		apolice.setValor(apoliceDto.getValor());
		apolice.setCliente(cliente);
		
		return apolice;
	}
	
}