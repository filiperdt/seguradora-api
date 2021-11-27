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
	@NotNull(message = "{cliente.cpf.notnull}")
	@NotBlank(message = "{cliente.cpf.notblank}")
	@CPF(message = "{cliente.cpf.cpf}")
	private String cpf;
	@NotNull(message = "{cliente.nome.notnull}")
	@NotBlank(message = "{cliente.nome.notblank}")
	private String nome;
	@NotNull(message = "{cliente.cidade.notnull}")
	@NotBlank(message = "{cliente.cidade.notblank}")
	private String cidade;
	@NotNull(message = "{cliente.uf.notnull}")
	@NotBlank(message = "{cliente.uf.notblank}")
	private String uf;
}
