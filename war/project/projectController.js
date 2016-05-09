Laboratory.controller('projectController', ['$scope', '$http','$routeParams', '$location','$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
	
	var reloadProject = function(){
		$http.get("/api/projects",{params: {id: $routeParams.projectId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.project = data;
		});
	}

	$scope.openSimulation = function(simulation){
		$location.path("/projects/"+$routeParams.projectId+"/simulations/"+simulation.id);
	}

	$scope.deleteSimulation = function(simulation){
		$http.delete("/api/simulations",{params:{simulation:simulation.id,projectId:$routeParams.projectId}}).success(function(data,status){
			reloadProject();
		});
	}
	
	$scope.createSimulation=function(){
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'createSimulationModal.html',
			controller: 'CreateSimulationController',
			resolve: {
			}
		});

		modalInstance.result.then(function (simulation) {
			$location.path("/projects/"+$routeParams.projectId+"/simulations/"+simulation.id);
		}, function () {
			console.log('Modal dismissed at: ' + new Date());
		});
	};

	reloadProject();
}]);

Laboratory.controller('CreateSimulationController', ['$scope', '$http', '$uibModalInstance','$routeParams',  function ($scope, $http, $uibModalInstance,$routeParams) {

	$scope.ok = function(){
		$http.post("/api/simulations",{projectId:$routeParams.projectId,name:$scope.name}).success(function(data,status){
			$uibModalInstance.close(data);
		});
	};

	$scope.cancel = function(){
		$uibModalInstance.dismiss('cancel');
	};
}]);