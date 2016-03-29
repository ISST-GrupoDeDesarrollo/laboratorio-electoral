Laboratory.directive('file', function(){
	return {
		scope: {
			file: '='
		},
		link: function(scope, element, attrs){
			element.bind('change', function(event){
				var file = event.target.files[0];
				scope.file = file ? file : undefined;
				scope.$apply();
			});
		}
	};
});


Laboratory.controller('registerController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){
		//register controller code goes here
		//$scope.controllerName = "registerController";
	$scope.usuario;
	$scope.contraseña;
	$scope.nombreCompleto;
	$scope.file;
	$scope.rol;
	$scope.email;
		
	$scope.enviarRegistro = ['$scope', function($scope){
			/*
			$http.get("/api/register/geturl").success(function(data,status){
				$scope.registerUrl = data.url;
			});
			*/
			
		$http({
			method: 'POST',
			url: 'direccion',
			headers: {
				'Content-Type': 'multipart/form-data'
			},
			data: {
				usuario: $scope.usuario,
				contraseña: $scope.contraseña,
				nombreCompleto: $scope.nombreCompleto,
				rol: $scope.rol,
				email: $scope.email,
				foto: $scope.file
			},
			transformRequest: function(data, headersGetter){
				var formData = new FormData();
				angular.forEach(data, function(value, key){
					formData.append(key,value);
				});

				var headers = headersGetter();
				delete headers['Content-Type'];

				return formData;
			}
		})
		.success(function(data){

		})
		.error(function(data, status){

		});


	}];
}]);
	
}]);
