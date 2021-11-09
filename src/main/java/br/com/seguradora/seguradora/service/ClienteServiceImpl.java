package br.com.seguradora.seguradora.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.seguradora.seguradora.dto.request.ClienteRequestDto;
import br.com.seguradora.seguradora.dto.response.ClienteResponseDto;
import br.com.seguradora.seguradora.model.Cliente;
import br.com.seguradora.seguradora.repository.ClienteRepository;
import net.minidev.json.JSONObject;

@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public static boolean validarLetras(String texto) {
		String regexp = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$"; // Permite letras (sem acentos), pontos (para abreviação) e espaços
		Pattern pattern = Pattern.compile(regexp,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(texto);
		
		return matcher.find();
	}
	
	public JSONObject verificaExistenciaCreate(String cpf, JSONObject msgResposta){
		if(this.clienteRepository.findByCpf(cpf) != null && cpf != "") {
			msgResposta.put("cpf", "CPF '" + cpf + "' já existe no banco!");
		}
		
		return msgResposta;
	}
	
	public JSONObject verificaExistenciaUpdate(String cpf, JSONObject msgResposta, Cliente cliente){
		if(this.clienteRepository.findByCpf(cpf) != null && cpf != "" && !cpf.equals(cliente.getCpf())) {
			msgResposta.put("cpf", "CPF '" + cpf + "' já existe no banco!");
		}
		
		return msgResposta;
	}
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteResponseDto> clientesDto = new ArrayList<>();
		
		clientes.stream().forEach(cliente -> {
			ClienteResponseDto clienteResponseDto = mapEntityParaDto(cliente);
			clientesDto.add(clienteResponseDto);
		});
		
		return ResponseEntity.ok().body(clientesDto);
	}

	@Transactional
	public ResponseEntity<?> create(ClienteRequestDto requisicao) {
		String nome = requisicao.getNome().trim();
		JSONObject msgResposta = new JSONObject();
		
		if(validarLetras(nome) == true) {
			String cpf = requisicao.getCpf();
			
			requisicao.setNome(nome);
			
			msgResposta = verificaExistenciaCreate(cpf, msgResposta);
			
			if(msgResposta.size() == 0) {
				Cliente clienteSalvo = this.clienteRepository.save(mapDtoParaEntity(requisicao, new Cliente(), false));
				ClienteResponseDto clienteResponseDto = mapEntityParaDto(clienteSalvo);
				return ResponseEntity.created(null).body(clienteResponseDto);
			}else {
				return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
			}
		}
		
		msgResposta.put("message", "Nome inválido!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> read(String cpf) {
		Optional<Cliente> optional = clienteRepository.findByCpf(cpf);
		
		if(optional.isPresent()) {
			ClienteResponseDto clienteResponseDto = mapEntityParaDto(optional.get());
			return ResponseEntity.ok().body(clienteResponseDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Cliente CPF "+cpf+" não encontrado no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(String cpf, ClienteRequestDto requisicao) {
		Optional<Cliente> optional = this.clienteRepository.findByCpf(cpf);
		JSONObject msgResposta = new JSONObject(); 
		
		if(optional.isPresent()) {
			String nome = requisicao.getNome().trim();
			
			if(validarLetras(nome) == true) {
				Cliente cliente = optional.get();
				String cpfVerificar = requisicao.getCpf();
				
				requisicao.setNome(nome);
				
				msgResposta = verificaExistenciaUpdate(cpfVerificar, msgResposta, cliente);
				
				if(msgResposta.size() == 0) {
					Cliente clienteSalvo = this.clienteRepository.save(mapDtoParaEntity(requisicao, optional.get(), true));
					ClienteResponseDto clienteResponseDto = mapEntityParaDto(clienteSalvo);
					return ResponseEntity.ok().body(clienteResponseDto);
				}else {
					return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
				}
			}
			
			msgResposta.put("message", "Nome inválido!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
		}
		
		msgResposta.put("message", "Cliente CPF "+cpf+" não encontrado no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(String cpf) {
		Optional<Cliente> optional = this.clienteRepository.findByCpf(cpf);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			try {
				this.clienteRepository.deleteByCpf(cpf);
				msgResposta.put("message", "Cliente CPF "+cpf+" excluído com sucesso!");
				return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
			} catch(Exception e) {
				msgResposta.put("message", "Falha na exclusão! Certifique-se de que o cliente CPF "+cpf+" não esteja relacionado em nenhuma apólice!");
				return retornaJsonMensagem(msgResposta, true, HttpStatus.CONFLICT);
			}
		}
		
		msgResposta.put("message", "Cliente CPF "+cpf+" não encontrado no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	public ClienteResponseDto mapEntityParaDto(Cliente cliente) {
		ClienteResponseDto clienteDto = ClienteResponseDto.builder()
		.cpf(cliente.getCpf())
		.nome(cliente.getNome())
		.cidade(cliente.getCidade())
		.uf(cliente.getUf())
		.build();
		
		return clienteDto;
	}
	
	public Cliente mapDtoParaEntity(ClienteRequestDto clienteDto, Cliente cliente, boolean update) {
		if(!update)
			cliente.setCpf(clienteDto.getCpf());
		
		cliente.setNome(clienteDto.getNome());
		cliente.setCidade(clienteDto.getCidade());
		cliente.setUf(clienteDto.getUf());
		
		return cliente;
	}
}
