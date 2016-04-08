package org.posjava.cliente;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.posjava.ResultadoOperacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/cliente")
public class ClienteController {

	@Autowired
    private ClienteService clienteService;
	
	@RequestMapping(value="/buscar/{id}", method=RequestMethod.GET)
	public Cliente buscarCliente(@PathVariable Long id) throws Exception {
		Cliente cliente = clienteService.buscarPorId(id);
		
		cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(cliente.getCodigo())).withSelfRel());
		cliente.add(linkTo(methodOn(ClienteController.class).criarCliente(cliente)).withRel("criar"));
		cliente.add(linkTo(methodOn(ClienteController.class).alterarCliente(cliente)).withRel("alterar"));
		cliente.add(linkTo(methodOn(ClienteController.class).excluirCliente(cliente.getCodigo())).withRel("deletar"));
		cliente.add(linkTo(methodOn(ClienteController.class).listarClientes()).withRel("listar"));
		
		return cliente;
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public List<Cliente> listarClientes() throws Exception {
	    List<Cliente> clientes = clienteService.buscarTodos();
	    
	    for(Cliente c :clientes){
            
            c.add(linkTo(methodOn(ClienteController.class).buscarCliente(c.getCodigo())).withRel("buscar"));
            c.add(linkTo(methodOn(ClienteController.class).criarCliente(c)).withRel("criar"));
            c.add(linkTo(methodOn(ClienteController.class).alterarCliente(c)).withRel("alterar"));
            c.add(linkTo(methodOn(ClienteController.class).excluirCliente(c.getCodigo())).withRel("deletar"));
            c.add(linkTo(methodOn(ClienteController.class).listarClientes()).withSelfRel());
        }
	    
	    return clientes;
	}
	
	@RequestMapping(value="/criar", method=RequestMethod.POST)
	public Cliente criarCliente(@RequestBody Cliente cliente) {
	    ResultadoOperacao resultado = new ResultadoOperacao();
        
        try {
            clienteService.salvar(cliente);
            
            cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(cliente.getCodigo())).withRel("buscar"));
            cliente.add(linkTo(methodOn(ClienteController.class).criarCliente(cliente)).withSelfRel());
            cliente.add(linkTo(methodOn(ClienteController.class).alterarCliente(cliente)).withRel("alterar"));
            cliente.add(linkTo(methodOn(ClienteController.class).excluirCliente(cliente.getCodigo())).withRel("deletar"));
            cliente.add(linkTo(methodOn(ClienteController.class).listarClientes()).withRel("listar"));
            
        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }
	    
		return cliente;
	}

	@RequestMapping(value="/alterar", method=RequestMethod.PUT)
	public Cliente alterarCliente(@RequestBody Cliente cliente) {
	    ResultadoOperacao resultado = new ResultadoOperacao();
        
        try {
            clienteService.salvar(cliente);
            
            cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(cliente.getCodigo())).withRel("buscar"));
            cliente.add(linkTo(methodOn(ClienteController.class).criarCliente(cliente)).withRel("criar"));
            cliente.add(linkTo(methodOn(ClienteController.class).alterarCliente(cliente)).withSelfRel());
            cliente.add(linkTo(methodOn(ClienteController.class).excluirCliente(cliente.getCodigo())).withRel("deletar"));
            cliente.add(linkTo(methodOn(ClienteController.class).listarClientes()).withRel("listar"));
            
        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }
        
		return cliente;
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.DELETE)
	public ResultadoOperacao excluirCliente(@PathVariable Long id) throws Exception {
	    ResultadoOperacao resultado = new ResultadoOperacao();
	    Boolean temNoPedido = clienteService.existePedidoComEsseCliente(id);

        if (temNoPedido) {
            throw new Exception();
        }
        
	    Cliente cliente = clienteService.buscarPorId(id);
	    
	    try {
	        clienteService.excluir(id);
	        
	        cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(cliente.getCodigo())).withRel("buscar"));
	        cliente.add(linkTo(methodOn(ClienteController.class).criarCliente(cliente)).withRel("criar"));
	        cliente.add(linkTo(methodOn(ClienteController.class).alterarCliente(cliente)).withRel("alterar"));
	        cliente.add(linkTo(methodOn(ClienteController.class).excluirCliente(cliente.getCodigo())).withSelfRel());
	        cliente.add(linkTo(methodOn(ClienteController.class).listarClientes()).withRel("listar"));
            
	    } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }
	    
        return resultado;
	}
	
	@RequestMapping(value = "/buscaPorNome", method = RequestMethod.GET)
    public List<Map<String, Object>> buscarPorNome(@RequestParam String pesquisa, @RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return clienteService.buscarPorNome(pesquisa, ordenarPor, ascDesc);
    }
	
	@RequestMapping(value = "/listarTodos", method = RequestMethod.GET)
    public List<Map<String, Object>> listarTodos(@RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return clienteService.listarTodos(ordenarPor, ascDesc);
    }

}