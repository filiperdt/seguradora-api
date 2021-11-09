package br.com.seguradora.seguradora.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class ApoliceResponseDto {
	private Long numero;
	private LocalDate inicioVigencia;
	private LocalDate fimVigencia;
	private String placaVeiculo;
	private BigDecimal valor;
}
