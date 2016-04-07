package org.posjava.produto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

@Entity
public class Produto extends ResourceSupport {
	
	@Id
	@GeneratedValue
	private Long codigo;
	private String nome;
	
	public Produto() {
	}

	public Produto(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}
	
}
