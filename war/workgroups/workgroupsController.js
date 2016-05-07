Laboratory.controller('workgroupsController', ['$scope', '$http', '$routeParams', '$location', '$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
		
	var reloadWorkgroups = function(){
		$http.get("/api/login").success(function(user){
			$scope.appUser = user;
			$http.get("/api/workgroups").success(function(data){
				$scope.cleanObjectFromDatabase(data);
				for (var i = data.length - 1; i >= 0; i--) {
					if(data[i].isPersonal){
						data.splice(i,1);
					}
				}
				$scope.workgroups = data;
				console.log(data);
			});
		});
	};

	$scope.addWorkgroup = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'createWorkgroupModal.html',
			controller: 'createWorkgroupController'
		});
		
		modalInstance.result.then(
			function(){
				reloadWorkgroups()
			}, function(){
				console.log("cancelado");
			}
		);
	}

	$scope.removeUser = function(workgroup,user){
		$http.delete("/api/workgroups",{params:{"removeUser":user.username,"workgroup":workgroup.id}}).success(function(data,status){
			reloadWorkgroups();
		});
	}

	reloadWorkgroups();

}]);


Laboratory.controller('createWorkgroupController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                         function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){
	
	$scope.ok = function () {
		$http.post("/api/workgroups",$scope.workgroup).success(function(data){		
			$uibModalInstance.close();
		});
			
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("Canceled");
	};
	
}]);
