Laboratory.controller('createProjectController', ['$scope', '$http','$routeParams', '$location', '$uibModal',
                                             function($scope,$http,$routeParams,$location,$uibModal){
	

	$http.get("/api/workgroups").success(function(data,status){
			
		$scope.cleanObjectFromDatabase(data);
		$scope.workgroups = data;
		$scope.workgroup = $scope.workgroups[0];
		
        
		});
	
}]);
