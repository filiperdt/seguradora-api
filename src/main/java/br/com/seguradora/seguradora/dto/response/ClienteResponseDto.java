package br.com.seguradora.seguradora.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteResponseDto {
	private String cpf;
	private String nome;
	private String cidade;
	private String uf;
}
