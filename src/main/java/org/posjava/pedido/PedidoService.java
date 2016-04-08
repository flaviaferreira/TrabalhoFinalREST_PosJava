package org.posjava.pedido;

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
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Pedido buscarPorId(Long id) {
        return pedidoRepo.findOne(id);
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepo.findAll();
    }

    public void salvar(Pedido pedido) {
        pedidoRepo.save(pedido);
    }

    public void excluir(Long id) {
        pedidoRepo.delete(id);
    }

    public List<Map<String, Object>> buscarPorNome(String pesquisa, String ordenarPor, String ascDesc) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pesquisa", "%" + pesquisa + "%");
        String query = "select p.codigo,"
                + " c.codigo as cliente_codigo,"
                + " c.nome as nome_cliente,"
                + " prod.codigo as produto_codigo,"
                + " prod.nome as nome_produto,"
                + " p.quantidade"
                + " from pedido p"
                + " join cliente c on c.codigo = p.cliente_codigo"
                + " join produto prod on prod.codigo = p.produto_codigo"
                + " where c.nome like :pesquisa or"
                + " prod.nome like :pesquisa"
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, params, new MapRowMapper());
    }

    public List<Map<String, Object>> listarTodos(String ordenarPor, String ascDesc) {
        String query = "select p.codigo,"
                + " c.codigo as cliente_codigo,"
                + " c.nome as nome_cliente,"
                + " prod.codigo as produto_codigo,"
                + " prod.nome as nome_produto,"
                + " p.quantidade"
                + " from pedido p"
                + " join cliente c on c.codigo = p.cliente_codigo"
                + " join produto prod on prod.codigo = p.produto_codigo"
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, new MapRowMapper());
    }

    public List<Map<String, Object>> buscaCliente(String parametro) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("parametro", "%" + parametro + "%");
        String familia
        = "select c.codigo,"
                + " c.nome"
                + " from cliente c"
                + " where c.nome like :parametro";
        return jdbcTemplate.query(familia, params, new MapRowMapper());
    }

    public List<Map<String, Object>> buscaProduto(String parametro) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("parametro", "%" + parametro + "%");
        String familia
        = "select p.codigo,"
                + " p.nome"
                + " from produto p"
                + " where p.nome like :parametro";
        return jdbcTemplate.query(familia, params, new MapRowMapper());
    }



}
