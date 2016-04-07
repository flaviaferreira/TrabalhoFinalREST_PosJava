package org.posjava.produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
	
	public Produto findByNome(String nome);
	
}
