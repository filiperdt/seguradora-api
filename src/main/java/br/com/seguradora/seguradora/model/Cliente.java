package br.com.seguradora.seguradora.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cliente {
	@Id
	@Column(nullable = false, unique = true)
	private String cpf;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cidade;
	@Column(nullable = false)
	private String uf;
	
	@JsonBackReference // Anotação necessária para não cair em loop infinito
	@OneToMany(mappedBy = "cliente")
	private Set<Apolice> apolices;
}
