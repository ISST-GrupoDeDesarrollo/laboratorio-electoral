Laboratory.controller('projectController', ['$scope', '$http','$routeParams', '$location','$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
	
	$http({
		method: 'GET',
		url: '/api/projects',
		headers: {'Content-Type': 'application/json'},
		params: {id: $routeParams.projectId}
	}).success(function(dataReturned){
		$scope.cleanObjectFromDatabase(dataReturned);
		$scope.project = dataReturned;
	}).error(function(data, status){
	});
	
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