package br.com.seguradora.seguradora.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

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
public class ClienteRequestDto {
	@CPF(message = "CPF inválido")
	private String cpf;
	@NotNull(message = "Nome não pode ser null")
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	@NotNull(message = "Cidade não pode ser null")
	@NotBlank(message = "Cidade é obrigatório")
	private String cidade;
	@NotNull(message = "UF não pode ser null")
	@NotBlank(message = "UF é obrigatório")
	private String uf;
}
