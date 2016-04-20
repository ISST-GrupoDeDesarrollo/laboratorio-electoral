Laboratory.controller('projectsController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	$scope.orderByField = 'name';
	$scope.reverseSort = false;
	
	$scope.newProject = function(){
		console.log("funcion llamada");
		$modal.open({
			templateUrl: '/projects/createProjectModal.html'
		});
	}
	
	
	
}]);
