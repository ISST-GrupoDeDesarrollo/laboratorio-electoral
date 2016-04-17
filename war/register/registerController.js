Laboratory.controller('registerController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){
		
	$scope.usuario;
	$scope.contraseña;
	$scope.nombreCompleto;
	$scope.file;
	$scope.rol;
	$scope.email;
		
	$scope.enviarRegistro = function(){
			
		var direccion = $http.get("/api/register/geturl").success(function(data,status){
			$scope.registerUrl = data.url;
			
			
			$http.get("/api/register/geturl").success(function(data,status){
	            console.log("solicitada la url");
	            $scope.registerUrl = data.url;
	            console.log(data.url);
	            
	            var fd = new FormData()
	            angular.forEach($scope.files, function(file){
	                fd.append('file', file)
	            })
	            $http.post(data.url, fd, 
	            {
	                transformRequest: angular.identity,
	                headers: {'Content-Type':'multipart/related'}
	            }).success(function(d){
	                console.log(d)
	            })
	        })
			
			/*
			$http({
				method: 'POST',
				url: direccion,
				headers: {
					'Content-Type': 'multipart/form-data'
				},
				data: {
					username: $scope.usuario,
					password: $scope.contraseña,
					completeName: $scope.nombreCompleto,
					role: $scope.rol,
					email: $scope.email,
					profilePic: $scope.file
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

			});*/

		});
			
		

	}];
	
}]);
