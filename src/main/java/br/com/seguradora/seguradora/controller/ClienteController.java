package br.com.seguradora.seguradora.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.seguradora.seguradora.dto.request.ClienteRequestDto;
import br.com.seguradora.seguradora.service.ClienteService;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/clientes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	public JSONObject analisaBindingResult(BindingResult bindingResult, JSONObject jsonMessages){
		List<FieldError> errors = bindingResult.getFieldErrors();
		for(FieldError erro : errors) {
			jsonMessages.put(erro.getField(), erro.getDefaultMessage());
		}
		
		return jsonMessages;
	}
	
	public ResponseEntity<JSONObject> geraErroDoBindingResult(BindingResult bindingResult) {
		JSONObject jsonMessages = new JSONObject();
		jsonMessages = analisaBindingResult(bindingResult, jsonMessages);
		jsonMessages.put("erro", true);
		
		return ResponseEntity.badRequest().body(jsonMessages);
	}
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<?> listAll(){
		ResponseEntity<?> responseEntity = clienteService.listAll();
		return responseEntity;
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<?> create(@Valid @RequestBody ClienteRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = clienteService.create(requisicao);
			return responseEntity;
		}
	}
	
	@GetMapping("/{cpf}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable String cpf) {
		ResponseEntity<?> responseEntity = clienteService.read(cpf);
		return responseEntity;
	}
	
	@PutMapping("/{cpf}")
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable String cpf, @Valid @RequestBody ClienteRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = clienteService.update(cpf, requisicao);
			return responseEntity;
		}
	}
	
	@DeleteMapping("/{cpf}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable String cpf) {
		ResponseEntity<?> responseEntity = clienteService.delete(cpf);
		return responseEntity;
	}
}
