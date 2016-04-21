Laboratory.controller('projectsController', ['$scope', '$http','$routeParams', '$location', '$uibModal',
                                             function($scope,$http,$routeParams,$location,$uibModal){
	$scope.orderByField = 'name';
	$scope.reverseSort = false;
	
	$scope.newProject = function(){
		$uibModal.open({
			templateUrl: '/projects/createProjectModal.html'
		});
	}
	
	
	
}]);
