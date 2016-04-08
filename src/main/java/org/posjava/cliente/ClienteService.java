package org.posjava.cliente;

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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Cliente buscarPorId(Long id) {
        return clienteRepo.findOne(id);
    }

    public List<Cliente> buscarTodos() {
        return clienteRepo.findAll();
    }

    public void salvar(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    public void excluir(Long id) {
        clienteRepo.delete(id);
    }

    public List<Map<String, Object>> buscarPorNome(String pesquisa, String ordenarPor, String ascDesc) {
        String query = "select c.codigo,"
                + " c.nome"
                + " from cliente c"
                + " where c.nome like '%" + pesquisa + "%' "
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, new MapRowMapper());
    }

    public List<Map<String, Object>> listarTodos(String ordenarPor, String ascDesc) {
        String query = "select c.codigo,"
                + " c.nome"
                + " from cliente c"
                + " order by " + ordenarPor + " " + ascDesc;
        return jdbcTemplate.query(query, new MapRowMapper());
    }

    public Boolean existePedidoComEsseCliente(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        String query = "select p.codigo,"
                + " p.quantidade,"
                + " p.cliente_codigo,"
                + " p.produto_codigo"
                + " from pedido p"
                + " where p.cliente_codigo = :id";

        List<Map<String, Object>> retorno = jdbcTemplate.query(query, params, new MapRowMapper());

        System.out.println(retorno);
        if ((retorno == null) || (retorno.isEmpty())){
            return false;
        } else {
            return true;
        }
    }
    
}