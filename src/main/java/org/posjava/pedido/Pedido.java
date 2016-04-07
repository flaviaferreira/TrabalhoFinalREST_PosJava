package org.posjava.pedido;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.posjava.cliente.Cliente;
import org.posjava.produto.Produto;
import org.springframework.hateoas.ResourceSupport;

@Entity
public class Pedido extends ResourceSupport {
	
	@Id
	@GeneratedValue
	private Long codigo;
	private Integer quantidade;
	
	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Produto produto;
	
	public Pedido() {
	}

    public Pedido(Integer quantidade, Cliente cliente, Produto produto) {
        super();
        this.quantidade = quantidade;
        this.cliente = cliente;
        this.produto = produto;
    }

    public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getCodigo() {
		return codigo;
	}

}
