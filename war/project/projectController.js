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
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
}]);

Laboratory.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, items) {
	
});