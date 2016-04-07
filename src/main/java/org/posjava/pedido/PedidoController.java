package org.posjava.pedido;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.posjava.ResultadoOperacao;
import org.posjava.cliente.Cliente;
import org.posjava.cliente.ClienteRepository;
import org.posjava.produto.Produto;
import org.posjava.produto.ProdutoRepository;
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
	PedidoRepository pedidoRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	ProdutoRepository produtoRepo;
	
	@RequestMapping(value="/buscar/{id}", method=RequestMethod.GET)
	public Pedido buscarPedido(@PathVariable Long id) {
	    Pedido pedido = pedidoRepo.findOne(id);
	    
	    pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withSelfRel());
        pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido.getQuantidade(), pedido.getCliente().getCodigo(), pedido.getProduto().getCodigo())).withRel("Criar PEDIDO - passar os parâmetros desejados"));
        pedido.add(linkTo(methodOn(PedidoController.class).excluirPedido(pedido.getCodigo())).withRel("Deletar PEDIDO"));
        pedido.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("Listar todos os PEDIDOS"));
	    
		return pedido;
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public List<Pedido> listarPedidos() {
		return pedidoRepo.findAll();
	}
	
	@RequestMapping(value="/criar", method=RequestMethod.POST)
	public Pedido criarPedido(@RequestParam Integer quantidade, @RequestParam Long clienteid, @RequestParam Long produtoid) {
	    ResultadoOperacao resultado = new ResultadoOperacao();
        Cliente cliente = clienteRepo.findOne(clienteid);
        Produto produto = produtoRepo.findOne(produtoid);
        Pedido pedido = new Pedido(quantidade, cliente, produto);

        try {
            pedidoRepo.save(pedido);

            pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withSelfRel());
            pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido.getQuantidade(), pedido.getCliente().getCodigo(), pedido.getProduto().getCodigo())).withRel("Criar PEDIDO - passar os parâmetros desejados"));
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
        Pedido pedido = pedidoRepo.findOne(id);
        
        try {
            pedidoRepo.delete(id);
            
            pedido.add(linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getCodigo())).withSelfRel());
            pedido.add(linkTo(methodOn(PedidoController.class).criarPedido(pedido.getQuantidade(), pedido.getCliente().getCodigo(), pedido.getProduto().getCodigo())).withRel("Criar PEDIDO - passar os parâmetros desejados"));
            pedido.add(linkTo(methodOn(PedidoController.class).excluirPedido(pedido.getCodigo())).withRel("Deletar PEDIDO"));
            pedido.add(linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("Listar todos os PEDIDOS"));
            
        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }
        
        return resultado;
    }
	
}
