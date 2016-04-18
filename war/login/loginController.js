Laboratory.controller('loginController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){

	$scope.sendLogin = function(){
		$http.post('/api/login',{
			username: $scope.usuario,
			password: $scope.contrasena
		}
	)
	.success(function(response){
	
	})
	.error(function(response, status){
	
	});
	};
  
}]);