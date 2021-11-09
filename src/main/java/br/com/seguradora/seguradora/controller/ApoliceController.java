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

import br.com.seguradora.seguradora.dto.request.ApoliceRequestDto;
import br.com.seguradora.seguradora.service.ApoliceService;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/apolices")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ApoliceController {
	@Autowired
	private ApoliceService apoliceService;
	
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
	public ResponseEntity<?> listAll() {
		ResponseEntity<?> responseEntity = apoliceService.listAll();
		return responseEntity;
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<?> create(@Valid @RequestBody ApoliceRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = apoliceService.create(requisicao);
			return responseEntity;
		}
	}
	
	@GetMapping("/{numero}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable Long numero) {
		ResponseEntity<?> responseEntity = apoliceService.read(numero);
		return responseEntity;
	}
	
	@PutMapping("/{numero}")
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable Long numero, @Valid @RequestBody ApoliceRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = apoliceService.update(numero, requisicao);
			return responseEntity;
		}
	}
	
	@DeleteMapping("/{numero}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable Long numero) {
		ResponseEntity<?> responseEntity = apoliceService.delete(numero);
		return responseEntity;
	}
	
	@GetMapping("/consultar-por-numero/{numero}")
	@ResponseBody
	public ResponseEntity<?> consultarPorNumero(@PathVariable Long numero) {
		ResponseEntity<?> responseEntity = apoliceService.consultarPorNumero(numero);
		return responseEntity;
	}
}