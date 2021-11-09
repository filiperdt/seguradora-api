package br.com.seguradora.seguradora.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

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
public class ApoliceRequestDto {
	@NotNull(message = "Fim da vigência não pode ser null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fimVigencia;
	@NotNull(message = "Placa do veículo não pode ser null")
	@NotBlank(message = "Placa do veículo é obrigatório")
	private String placaVeiculo;
	@NotNull(message = "Valor não pode ser null")
	@PositiveOrZero(message = "Valor não pode ser negativo")
	private BigDecimal valor;
	
	@NotNull(message = "Cliente não pode ser null")
	@NotBlank(message = "Cliente é obrigatório")
	private String cliente;
}
