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
				console.log(dataReturned);
				// ir a project creado
			}, function(){
				console.log("cancelado");
			}
		);
	}
	
}]);




Laboratory.controller('modalController', ['$scope', '$http', '$uibModalInstance', function($scope, $http, $uibModalInstance){
	
	$http.get("/api/workgroups").success(function(data,status){
		
		$scope.cleanObjectFromDatabase(data);
		$scope.workgroups = data;
		$scope.workgroup = $scope.workgroups[0];    
	});
	
	$scope.ok = function () {
		console.log($scope.nombreProyecto);
		console.log($scope.descripcion);
		// Enviar workgroup
		$http({
			method: 'POST',
			url: '/api/projects/' + workgroupId,
			data: JSON.stringify({
				name: $scope.nombreProyecto,
				description: $scope.descripcion
			}),
			headers: {'Content-Type': 'application/json'},
			params: {workgroupId: $workgroup.id}
		}).success(function(dataReturned){
			$scope.cleanObjectFromDatabase(dataReturned);
			$uibModalInstance.close(dataReturned);
		}).error(function(data, status){
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
