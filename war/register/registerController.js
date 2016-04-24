Laboratory.controller('registerController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){
		
	$scope.enviarRegistro = function(){
			$http.get("/api/register/geturl").success(function(data,status){
	            $scope.registerUrl = data.url;
	            
	            var formData = new FormData();
	            formData.append('profilePic',$scope.files[0])
        		formData.append('username', $scope.usuario)
        		formData.append('password', $scope.password)
        		formData.append('completeName', $scope.nombreCompleto)
        		formData.append('role', $scope.rol)
        		formData.append('email',$scope.email);
	            
	            $http.post(data.url, formData, {
	            	transformRequest: angular.identity,
	            	headers: {'Content-Type': undefined}
	            })
				.success(function(data){
					$location.path("/projects");
				}).error(function(data, status){
					if(status===403){
						alert("The username is in use");
					}
				}); 
	        });
	};
	
}]);
