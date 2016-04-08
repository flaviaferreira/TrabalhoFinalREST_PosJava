package org.posjava.produto;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.posjava.MapRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Produto buscarPorId(Long id) {
        return produtoRepo.findOne(id);
    }

    public List<Produto> buscarTodos() {
        return produtoRepo.findAll();
    }

    public void salvar(Produto produto) {
        produtoRepo.save(produto);
    }

    public void excluir(Long id) {
        produtoRepo.delete(id);
    }

    public List<Map<String, Object>> buscarPorNome(String pesquisa, String ordenarPor, String ascDesc) {
        String query = "select p.codigo,"
                + " p.nome"
                + " from produto p"
                + " where p.nome like '%" + pesquisa + "%' "
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, new MapRowMapper());
    }

    public List<Map<String, Object>> listarTodos(String ordenarPor, String ascDesc) {
        String query = "select p.codigo,"
                + " p.nome"
                + " from produto p"
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, new MapRowMapper());
    }

    public boolean existePedidoComEsseProduto(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        String query = "select p.codigo,"
                + " p.quantidade,"
                + " p.cliente_codigo,"
                + " p.produto_codigo"
                + " from pedido p"
                + " where p.produto_codigo = :id";

        List<Map<String, Object>> retorno = jdbcTemplate.query(query, params, new MapRowMapper());

        System.out.println(retorno);
        if ((retorno == null) || (retorno.isEmpty())){
            return false;
        } else {
            return true;
        }
    }

}
