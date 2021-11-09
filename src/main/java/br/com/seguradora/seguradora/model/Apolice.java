package br.com.seguradora.seguradora.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class Apolice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long numero;
	@Column(nullable = false)
	private LocalDate inicioVigencia;
	@Column(nullable = false)
	private LocalDate fimVigencia;
	@Column(nullable = false)
	private String placaVeiculo;
	@Column(nullable = false)
	private BigDecimal valor;
	
	@JsonManagedReference // Anotação necessária para não cair em loop infinito
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false) // A anotação @JoinColumn name é o nome que esta coluna terá no BD
	private Cliente cliente;
}
