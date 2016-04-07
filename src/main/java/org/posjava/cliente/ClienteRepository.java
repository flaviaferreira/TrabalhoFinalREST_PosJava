package org.posjava.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{
	
	public Cliente findByNome(String nome);
	
}
