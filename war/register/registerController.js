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
	            formData.append('profilePic',$scope.file)
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
	            
	        })
			
	        /*
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
            })*/
	        
			/*
			$http({
				method: 'POST',
				url: direccion,
				headers: {
					'Content-Type': 'multipart/form-data'
				},
				data: {
					username: $scope.usuario,
					password: $scope.password,
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
	        
	    
			
	};
	
}]);
