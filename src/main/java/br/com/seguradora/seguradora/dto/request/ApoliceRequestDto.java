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
	@NotNull(message = "{apolice.fimVigencia.notnull}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fimVigencia;
	@NotNull(message = "{apolice.placaVeiculo.notnull}")
	@NotBlank(message = "{apolice.placaVeiculo.notblank}")
	private String placaVeiculo;
	@NotNull(message = "{apolice.valor.notnull}")
	@PositiveOrZero(message = "{apolice.valor.positiveorzero}")
	private BigDecimal valor;
	
	@NotNull(message = "{apolice.cliente.notnull}")
	@NotBlank(message = "{apolice.cliente.notblank}")
	private String cliente;
}
