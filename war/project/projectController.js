Laboratory.controller('projectController', ['$scope', '$http','$routeParams', '$location','$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
	
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

Laboratory.controller('CreateSimulationController', ['$scope', '$http', '$uibModalInstance',  function ($scope, $http, $uibModalInstance) {

	$scope.ok = function(){
		$http.put("/api/simulations",{team:"TODO",simulname:$scope.simulname}).success(function(data,status){
					$uibModalInstance.close(data);
				});
	};

	$scope.cancel = function(){
		$uibModalInstance.dismiss('cancel');
	};
}]);