package org.posjava.pedido;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.posjava.ResultadoOperacao;
import org.posjava.cliente.Cliente;
import org.posjava.cliente.ClienteService;
import org.posjava.produto.Produto;
import org.posjava.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value="/buscar/{id}", method=RequestMethod.GET)
    public Pedido buscarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);

        pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withSelfRel());
        //        pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido)).withRel("Criar PEDIDO - passar os parâmetros desejados"));
        pedido.add(linkTo(methodOn(PedidoController.class).excluirPedido(pedido.getCodigo())).withRel("Deletar PEDIDO"));
        pedido.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("Listar todos os PEDIDOS"));

        return pedido;
    }

    @RequestMapping(value="/listar", method=RequestMethod.GET)
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodos();

        for(Pedido p :pedidos){

            p.add(linkTo(methodOn(PedidoController.class).buscarPedido(p.getCodigo())).withRel("Buscar PEDIDO"));
            //          pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido)).withRel("Criar PEDIDO - passar os parâmetros desejados"));
            p.add(linkTo(methodOn(PedidoController.class).excluirPedido(p.getCodigo())).withRel("Deletar PEDIDO"));
            p.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withSelfRel());
        }

        return pedidos;

    }

    @RequestMapping(value="/criar/{quantidade}/{cliente_codigo}/{produto_codigo}", method=RequestMethod.POST)
    public Pedido criarPedido(@PathVariable int quantidade, @PathVariable Long cliente_codigo, @PathVariable Long produto_codigo) {
        ResultadoOperacao resultado = new ResultadoOperacao();
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.buscarPorId(cliente_codigo);
        Produto produto = produtoService.buscarPorId(produto_codigo);
        pedido.setQuantidade(quantidade);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);

        try {
            pedidoService.salvar(pedido);

            pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withRel("Buscar PEDIDO"));
            //            pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido)).withSelfRel());
            pedido.add(linkTo(methodOn(PedidoController.class).excluirPedido(pedido.getCodigo())).withRel("Deletar PEDIDO"));
            pedido.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("Listar todos os PEDIDOS"));

        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }

        return pedido;
    }

    @RequestMapping(value="/excluir/{id}", method=RequestMethod.DELETE)
    public ResultadoOperacao excluirPedido(@PathVariable Long id) {
        ResultadoOperacao resultado = new ResultadoOperacao();
        Pedido pedido = pedidoService.buscarPorId(id);

        try {
            pedidoService.excluir(id);

            pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withRel("Buscar PEDIDO"));
            //            pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido)).withRel("Criar PEDIDO - passar os parâmetros desejados"));
            pedido.add(linkTo(methodOn(PedidoController.class).excluirPedido(pedido.getCodigo())).withSelfRel());
            pedido.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("Listar todos os PEDIDOS"));

        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }

        return resultado;
    }

    @RequestMapping(value = "/buscaPorNome", method = RequestMethod.GET)
    public List<Map<String, Object>> buscarPorNome(@RequestParam String pesquisa, @RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return pedidoService.buscarPorNome(pesquisa, ordenarPor, ascDesc);
    }

    @RequestMapping(value = "/listarTodos", method = RequestMethod.GET)
    public List<Map<String, Object>> listarTodos(@RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return pedidoService.listarTodos(ordenarPor, ascDesc);
    }

    @RequestMapping(value = "/buscaCliente/{parametro}", method = RequestMethod.GET)
    public List<Map<String, Object>> buscaCliente(@PathVariable String parametro) {
        return pedidoService.buscaCliente(parametro);
    }

    @RequestMapping(value = "/buscaProduto/{parametro}", method = RequestMethod.GET)
    public List<Map<String, Object>> buscaProduto(@PathVariable String parametro) {
        return pedidoService.buscaProduto(parametro);
    }
}
