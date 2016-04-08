app.controller("ProdutoController", ["$scope", "$http", "$rootScope", "$location", function ($scope, $http, $rootScope, $location) {

	$scope.produto = {};
	$scope.listaDeProdutos = [];
	$scope.nomeProduto;
	$scope.colunaOrdenar = "nome";
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
		if ($scope.nomeProduto == "" || $scope.nomeProduto == null) {
			$http.get("/produto/listarTodos?&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
			.success(function (data) {
				$scope.listaDeProdutos = data;
			})
			.error(function (data) {
				toastr.error("Erro ao encontrar os dados cadastrados.");
			});
		} else {
			$http.get("/produto/buscaPorNome?pesquisa=" + $scope.nomeProduto + "&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
			.success(function (data) {
				$scope.listaDeProdutos = data;
			})
			.error(function () {
				toastr.error("Erro ao realizar a pesquisa.");
			});
		}
	};

	 $scope.selecionaItem = function(p){
         $scope.produto = angular.copy(p);
     };
     
	$scope.adicionarProduto = function(){
		$http.post("/produto/criar/", $scope.produto)
		.success(function(){
			toastr.success("Produto salvo com sucesso!");
			$scope.listar();
		})
		.error(function(){
			toastr.error("Erro ao salvar o produto.");
		});
	};

	$scope.alterarProduto = function(){
		$http.put("/produto/alterar/", $scope.produto)
		.success(function () {
			toastr.success("Produto alterado com sucesso!");
			$scope.listar();
		})
		.error(function () {
			toastr.error("Erro ao alterar o produto.");
		});
	};

	$scope.excluirProduto = function () {
		$http.delete("/produto/excluir/" + $scope.produto.codigo)
		.success(function () {
			toastr.success("Produto excluído com sucesso!");
			$scope.listar();
		})
		.error(function () {
			toastr.error("Erro ao excluir o produto. Verifique se esse produto está vinculado à algum pedido.");
		});
	};
	
	function init() {
        $scope.listar();
    };

	init();

}]);