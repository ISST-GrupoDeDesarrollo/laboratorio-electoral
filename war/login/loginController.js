Laboratory.controller('loginController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){

	$scope.sendLogin = function(){
		$http.post('/api/login',{
			username: $scope.usuario,
			password: $scope.contrasena
		}).success(function(data){
			$location.path("/projects");
			  $scope.$emit("loginChanged");
		}).error(function(data, status){
			alert("Wrong username or password");
		});
	};
  
}]);