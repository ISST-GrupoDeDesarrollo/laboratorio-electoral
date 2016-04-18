Laboratory.controller('registerController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){
	/*	
	$scope.usuario;
	$scope.password;
	$scope.nombreCompleto;
	$scope.file;
	$scope.rol;
	$scope.email;*/
		
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
					console.log('sent');
				})
				.error(function(data, status){
					console.log('not sent');
				});
	            
	        });
	        
	    
			
	};
	
}]);
