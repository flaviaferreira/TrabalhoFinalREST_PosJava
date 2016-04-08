var app = angular.module('app', ['ngRoute', 'ui.bootstrap']);

app.config(function($routeProvider, $locationProvider){
//	// remove o # da url
//	$locationProvider.html5Mode(true);
	$routeProvider
	
	.when('/cliente', {
		templateUrl : 'html/cliente.html',
		controller  : 'ClienteController',
	})
	
	.when('/produto', {
		templateUrl : 'html/produto.html',
		controller  : 'ProdutoController',
	})
	
	.when('/pedido', {
		templateUrl : 'html/pedido.html',
		controller  : 'PedidoController',
	})

	// caso não seja nenhum desses, redirecione para a rota '/'
	.otherwise ({ redirectTo: '/' });
});


