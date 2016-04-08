package org.posjava.produto;

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
@RequestMapping(value="/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value="/buscar/{id}", method=RequestMethod.GET)
    public Produto buscarProduto(@PathVariable Long id) throws Exception {
        Produto produto = produtoService.buscarPorId(id);

        produto.add(linkTo(methodOn(ProdutoController.class).buscarProduto(produto.getCodigo())).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).criarProduto(produto)).withRel("criar"));
        produto.add(linkTo(methodOn(ProdutoController.class).alterarProduto(produto)).withRel("alterar"));
        produto.add(linkTo(methodOn(ProdutoController.class).excluirProduto(produto.getCodigo())).withRel("deletar"));
        produto.add(linkTo(methodOn(ProdutoController.class).listarProdutos()).withRel("listar"));

        return produto;
    }

    @RequestMapping(value="/listar", method=RequestMethod.GET)
    public List<Produto> listarProdutos() throws Exception {
        List<Produto> produtos = produtoService.buscarTodos();

        for(Produto p :produtos){

            p.add(linkTo(methodOn(ProdutoController.class).buscarProduto(p.getCodigo())).withRel("buscar"));
            p.add(linkTo(methodOn(ProdutoController.class).criarProduto(p)).withRel("criar"));
            p.add(linkTo(methodOn(ProdutoController.class).alterarProduto(p)).withRel("alterar"));
            p.add(linkTo(methodOn(ProdutoController.class).excluirProduto(p.getCodigo())).withRel("deletar"));
            p.add(linkTo(methodOn(ProdutoController.class).listarProdutos()).withSelfRel());
        }

        return produtos;
    }

    @RequestMapping(value="/criar", method=RequestMethod.POST)
    public Produto criarProduto(@RequestBody Produto produto) {
        ResultadoOperacao resultado = new ResultadoOperacao();

        try {
            produtoService.salvar(produto);

            produto.add(linkTo(methodOn(ProdutoController.class).buscarProduto(produto.getCodigo())).withRel("buscar"));
            produto.add(linkTo(methodOn(ProdutoController.class).criarProduto(produto)).withSelfRel());
            produto.add(linkTo(methodOn(ProdutoController.class).alterarProduto(produto)).withRel("alterar"));
            produto.add(linkTo(methodOn(ProdutoController.class).excluirProduto(produto.getCodigo())).withRel("deletar"));
            produto.add(linkTo(methodOn(ProdutoController.class).listarProdutos()).withRel("listar"));

        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }

        return produto;
    }

    @RequestMapping(value="/alterar", method=RequestMethod.PUT)
    public Produto alterarProduto(@RequestBody Produto produto) {
        ResultadoOperacao resultado = new ResultadoOperacao();

        try {
            produtoService.salvar(produto);

            produto.add(linkTo(methodOn(ProdutoController.class).buscarProduto(produto.getCodigo())).withRel("buscar"));
            produto.add(linkTo(methodOn(ProdutoController.class).criarProduto(produto)).withRel("criar"));
            produto.add(linkTo(methodOn(ProdutoController.class).alterarProduto(produto)).withSelfRel());
            produto.add(linkTo(methodOn(ProdutoController.class).excluirProduto(produto.getCodigo())).withRel("deletar"));
            produto.add(linkTo(methodOn(ProdutoController.class).listarProdutos()).withRel("listar"));

        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }

        return produto;
    }

    @RequestMapping(value="/excluir/{id}", method=RequestMethod.DELETE)
    public ResultadoOperacao excluirProduto(@PathVariable Long id) throws Exception {
        ResultadoOperacao resultado = new ResultadoOperacao();
        Boolean temNoPedido = produtoService.existePedidoComEsseProduto(id);

        if (temNoPedido) {
            throw new Exception();
        }

        Produto produto = produtoService.buscarPorId(id);
        
        try {
            produtoService.excluir(id);

            produto.add(linkTo(methodOn(ProdutoController.class).buscarProduto(produto.getCodigo())).withRel("buscar"));
            produto.add(linkTo(methodOn(ProdutoController.class).criarProduto(produto)).withRel("criar"));
            produto.add(linkTo(methodOn(ProdutoController.class).alterarProduto(produto)).withRel("alterar"));
            produto.add(linkTo(methodOn(ProdutoController.class).excluirProduto(produto.getCodigo())).withSelfRel());
            produto.add(linkTo(methodOn(ProdutoController.class).listarProdutos()).withRel("listar"));

        } catch (Exception e) {
            resultado.setMessage(e.getMessage());
            resultado.setStatus(ResultadoOperacao.FALHA);
        }

        return resultado;
    }

    @RequestMapping(value = "/buscaPorNome", method = RequestMethod.GET)
    public List<Map<String, Object>> buscarPorNome(@RequestParam String pesquisa, @RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return produtoService.buscarPorNome(pesquisa, ordenarPor, ascDesc);
    }

    @RequestMapping(value = "/listarTodos", method = RequestMethod.GET)
    public List<Map<String, Object>> listarTodos(@RequestParam String ordenarPor, @RequestParam String ascDesc) {
        return produtoService.listarTodos(ordenarPor, ascDesc);
    }
    
}
