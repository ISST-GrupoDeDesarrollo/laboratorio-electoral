Laboratory.controller('projectController', ['$scope', '$http','$routeParams', '$location','$uibModal','breadcrumbs', function($scope,$http,$routeParams,$location,$uibModal,breadcrumbs){
	breadcrumbs.setBreadcrumbs([breadcrumbs.createBreadcrumb("project", {projectId:$routeParams.projectId})]);
	$scope.$on("checkAuth",function(){
		if(!$scope.appUser){
			$location.path("/");
		}
	});

	var reloadProject = function(){
		$http.get("/api/projects",{params: {id: $routeParams.projectId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.project = data;
			getActualWorkgroup();
		});
	}

	
	var getActualWorkgroup = function(){
		$http.get("api/workgroups").success(function(data){
			var actualProjectId = $scope.project.id;
			$scope.cleanObjectFromDatabase(data);
			$scope.workgroupsRecibidos = data;
			for(i in $scope.workgroupsRecibidos){
				var workgroupSelec = $scope.workgroupsRecibidos[i];
				for(j in workgroupSelec.projects){
					if(actualProjectId === workgroupSelec.projects[j].id){
						$scope.actualWorkgroup = workgroupSelec;
						break;
					}
				}
			}
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
			animation: true,
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

	$scope.addMessage = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'addMessageModal.html',
			controller: 'addMessageController',
			resolve: {
			}
		});

		modalInstance.result.then(function (simulation) {
			reloadProject();
		}, function () {
			console.log('Modal dismissed at: ' + new Date());
		});
	}

	$scope.deleteMessage = function(message){
		$http.delete("/api/messages",{params:{messageId:message.id,projectId:$routeParams.projectId}}).success(function(data,status){
			reloadProject();
		});
	}

	$scope.openReport = function(report){
		$location.path("/projects/"+$routeParams.projectId+"/results/"+report.id);
	}

	$scope.deleteReport = function(report){
		$http.delete("/api/reports",{params:{reportId:report.id,projectId:$routeParams.projectId}}).success(function(data,status){
			reloadProject();
		});
	}

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

Laboratory.controller('addMessageController', ['$scope', '$http', '$uibModalInstance','$routeParams',  function ($scope, $http, $uibModalInstance,$routeParams) {

	$scope.ok = function(){
		if($scope.addMessageForm.$valid){
		$http.post("/api/messages",$scope.message,{params:{projectId:$routeParams.projectId}}).success(function(data,status){
			$uibModalInstance.close(data);
		});
	}
	};

	$scope.cancel = function(){
		$uibModalInstance.dismiss('cancel');
	};
}]);