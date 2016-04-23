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
			function(nombrePro){
				console.log("enviado");
				console.log(nombrePro);
			}, function(){
				console.log("cancelado");
			}
		);
	}
	
}]);




Laboratory.controller('modalController', ['$scope', '$http', '$uibModalInstance', function($scope, $http, $uibModalInstance){
	
	$scope.ok = function () {
	   //Enviar peticion http
	   $uibModalInstance.close($scope.nombreProyecto);
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("cancelado");
	};
	
}]);
