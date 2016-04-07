app.controller("ClienteController", ["$scope", "$http", "$rootScope", "$location", function ($scope, $http, $rootScope, $location) {

	$scope.cliente = {};
	$scope.listaDeClientes = [];
	$scope.nomeCliente;
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
		console.log($scope.colunaOrdenar);
	};

	$scope.listar = function (ordenar, ascDesc) {
		if (ordenar == undefined) {
			ordenar = $scope.colunaOrdenar;
		}
		if (ascDesc == undefined) {
			ascDesc = $scope.ordem;
		}
		if ($scope.nomeCliente == "" || $scope.nomeCliente == null) {
			$http.get("/cliente/listarTodos?&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
			.success(function (data) {
				$scope.listaDeClientes = data;
			})
			.error(function (data) {
				toastr.error("Erro ao encontrar os dados cadastrados.");
			});
		} else {
			$http.get("/cliente/buscaPorNome?pesquisa=" + $scope.nomeCliente + "&ordenarPor=" + ordenar + "&ascDesc=" + ascDesc)
			.success(function (data) {
				$scope.listaDeComunidades = data;
			})
			.error(function () {
				toastr.error("Erro ao realizar a pesquisa.");
			});
		}
		console.log($scope.listaDeClientes);
	};

	 $scope.selecionaItem = function(c){
         $scope.cliente = angular.copy(c);
     };
     
	$scope.adicionarCliente = function(){
		$http.post("/cliente/criar/", $scope.cliente)
		.success(function(){
			toastr.success("Cliente salvo com sucesso!");
			$scope.listar();
		})
		.error(function(){
			toastr.error("Erro ao salvar o cliente.");
		});
	};

	$scope.alterarCliente = function(){
		$http.put("/cliente/alterar/", $scope.cliente)
		.success(function () {
			toastr.success("Cliente alterado com sucesso!");
			$scope.listar();
		})
		.error(function () {
			toastr.error("Erro ao alterar o cliente.");
		});
	};

	$scope.excluirCliente = function () {
		$http.delete("/cliente/excluir/" + $scope.cliente.codigo)
		.success(function () {
			toastr.success("Cliente excluído com sucesso!");
			$scope.listar();
		})
		.error(function () {
			toastr.error("Erro ao excluir o cliente. Verifique se esse cliente está vinculado à algum pedido.");
		});
	};
	
	function init() {
        $scope.listar();
    };

	init();

}]);