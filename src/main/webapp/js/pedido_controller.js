app.controller("PedidoController", ["$scope", "$http", "$rootScope", "$location", function ($scope, $http, $rootScope, $location) {

	$scope.pedido = {};
	$scope.listaDePedidos = [];
	$scope.nomeCliente;
	$scope.nomeProduto;
	$scope.cliente;
	$scope.produto;
	$scope.quantidade;
	$scope.colunaOrdenar = "nome_cliente";
	$scope.ordem = "ASC";

	$scope.ascdescparameter = function () {
		if ($scope.ordem == "ASC") {
			$scope.ordem = "DESC";
		}
		else if ($scope.ordem == "DESC") {
			$scope.ordem = "ASC";
		}
	};

	$scope.ordenar = function (coluna) {
		$scope.colunaOrdenar = coluna;
		$scope.ascdescparameter();
		$scope.listar();
	};

	$scope.listar = function (ordenar, ascDesc) {
		if (ordenar == undefined) {
			ordenar = $scope.colunaOrdenar;
		}
		if (ascDesc == undefined) {
			ascDesc = $scope.ordem;
		}
		if ($scope.nomeCliente == "" || $scope.nomeCliente == null || $scope.nomeProduto == "" || $scope.nomeProduto == null) {
			$http.get("/pedido/listarTodos?&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
			.success(function (data) {
				$scope.listaDePedidos = data;
				console.log("pedido", $scope.pedido);
				console.log("data", data);
			})
			.error(function (data) {
				toastr.error("Erro ao encontrar os dados cadastrados.");
			});
		} else {
			if ($scope.nomeCliente != "" || $scope.nomeCliente != null && $scope.nomeProduto == "" || $scope.nomeProduto == null){
				$http.get("/pedido/buscaPorNome?pesquisa=" + $scope.nomeCliente + "&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
				.success(function (data) {
					$scope.listaDePedidos = data;
				})
				.error(function () {
					toastr.error("Erro ao realizar a pesquisa.");
				});
			} else {
				if ($scope.nomeProduto != "" || $scope.nomeProduto != null && $scope.nomeCliente == "" || $scope.nomeCliente == null){
					$http.get("/pedido/buscaPorNome?pesquisa=" + $scope.nomeProduto + "&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
					.success(function (data) {
						$scope.listaDePedidos = data;
					})
					.error(function () {
						toastr.error("Erro ao realizar a pesquisa.");
					});
				}
			}
		}
	};

	$scope.selecionaItem = function(p){
		$scope.pedido = angular.copy(p);
	};

	$scope.adicionarPedido = function(){
		$scope.pedido.quantidade = $scope.quantidade;
		$scope.pedido.cliente_codigo = $scope.cliente.codigo;
		$scope.pedido.produto_codigo = $scope.produto.codigo;
		
		console.log("$scope.pedido",$scope.pedido);
		
		$http.post("/pedido/criar/" + $scope.pedido.quantidade + "/" + $scope.pedido.cliente_codigo + "/" + $scope.pedido.produto_codigo)
		.success(function(){
			toastr.success("Pedido salvo com sucesso!");
			$scope.listar();
		})
		.error(function(){
			toastr.error("Erro ao salvar o pedido.");
		});
	};

	$scope.excluirPedido = function () {
		$http.delete("/pedido/excluir/" + $scope.pedido.codigo)
		.success(function () {
			toastr.success("Pedido excluído com sucesso!");
			$scope.listar();
		})
		.error(function () {
			toastr.error("Erro ao excluir o pedido.");
		});
	};

	//typeahead do input do cliente
	$scope.buscarCliente = function (val) {
		return $http.get('/pedido/buscaCliente/' + val)
		.then(function (response) {
			return response.data;
		});
	};

	//typeahead do input do produto
	$scope.buscarProduto = function (val) {
		return $http.get('/pedido/buscaProduto/' + val)
		.then(function (response) {
			return response.data;
		});
	};

	function init() {
		$scope.listar();
	};

	init();

}]);