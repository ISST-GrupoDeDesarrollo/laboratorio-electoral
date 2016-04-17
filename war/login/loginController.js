Laboratory.controller('loginController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	var user = $scope.usuario;
	var contr = $scope.contrasena;	
	
	$scope.sendLogin = function(){
		$http.post('/api/login',{
			username: user,
			password: contr
		}
	)
	.success(function(response){
	
	})
	.error(function(response, status){
	
	});
	};
  
}]);