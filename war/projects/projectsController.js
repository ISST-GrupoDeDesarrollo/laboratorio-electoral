Laboratory.controller('projectsController', ['$scope', '$http','$routeParams', '$location', '$uibModal',
                                             function($scope,$http,$routeParams,$location,$uibModal){
	

	$http.get("/api/projects").success(function(data,status){
			
		$scope.cleanObjectFromDatabase(data);
		$scope.projects = data;
        
	});
	
	$scope.orderByField = 'name';
	$scope.reverseSort = false;
	
	$scope.animationsEnabled = true;
	$scope.newProject = function(){
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'modalView.html',
			controller: 'modalController'
		});
		
		modalInstance.result.then(
			function(dataReturned){
				console.log("enviado");
				console.log(dataReturned.nombreProyecto);
				//$scope.projects = dataReturned
			}, function(){
				console.log("cancelado");
			}
		);
	}
	
}]);




Laboratory.controller('modalController', ['$scope', '$http', '$uibModalInstance', function($scope, $http, $uibModalInstance){
	
	var data = {
			nombreProyecto: $scope.nombreProyecto,
			nombreTrabajo: $scope.nombreTrabajo,
			descripcion: $scope.descripcion
	}
	
	$scope.ok = function () {
		$http({
			method: 'Post',
			url: '/api/projects',
			data: data
		}).success(function(dataReturned){
			$uibModalInstance.close(dataReturned);
		}).error(function(status){
			if(status == 401){
				alert("Acceso denegado sin sesión");
			}else if(status == 400){
				alert("Complete todos los campos");
			}
		});
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("cancelado");
	};
	
}]);
