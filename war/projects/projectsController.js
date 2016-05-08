Laboratory.controller('projectsController', ['$scope', '$http','$routeParams', '$location', '$uibModal',
                                             function($scope,$http,$routeParams,$location,$uibModal){
	
	$scope.projects = [];
	var reloadProjects = function(){
		$http.get("/api/workgroups").success(function(data,status){
			$scope.projects = [];
			$scope.cleanObjectFromDatabase(data);
			$scope.workgroupsRecibidos = data;
			for(i in $scope.workgroupsRecibidos){
				var workgroupSelec = $scope.workgroupsRecibidos[i];
				for(j in workgroupSelec.projects){
					var project = workgroupSelec.projects[j];
					project.workgroup = {name:workgroupSelec.name};
					$scope.projects.push(project);
				}
			}
		});
	};
	
	reloadProjects();
	
	$scope.orderByField = 'name';
	$scope.reverseSort = false;
	
	$scope.animationsEnabled = true;
	$scope.newProject = function(){
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'createProjectModal.html',
			controller: 'createProjectController'
		});
		
		modalInstance.result.then(
			function(projectCreated){
				$scope.goToProject(projectCreated)
			}, function(){
				console.log("cancelado");
			}
		);
	}
	
	
	$scope.goToProject = function(projectSelected){
		$location.path("/projects/" + projectSelected);
	}

	$scope.deleteProject = function(projectSelected){
		$http.delete("/api/projects", {params:{id:projectSelected}})
		.success(function(e){
			reloadProjects();
		});
	}
	
}]);




Laboratory.controller('createProjectController', ['$scope', '$http', '$uibModalInstance', function($scope, $http, $uibModalInstance){
	
	$http.get("/api/workgroups").success(function(data,status){
		
		$scope.cleanObjectFromDatabase(data);
		$scope.workgroups = data;
		$scope.workgroup = $scope.workgroups[0];    
	});
	
	$scope.ok = function () {
		$http({
			method: 'POST',
			url: '/api/projects',
			data: JSON.stringify({
				name: $scope.nombreProyecto,
				description: $scope.descripcion
			}),
			headers: {'Content-Type': 'application/json'},
			params: {workgroupId: $scope.workgroup.id}
		}).success(function(dataReturned){
			$scope.cleanObjectFromDatabase(dataReturned);
			$uibModalInstance.close(dataReturned);
		}).error(function(data, status){
			if(status == 401){
				alert("Acceso denegado sin sesión");
			}else if(status == 400){
				alert("Complete todos los campos");
			}
		});
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("cancelado");
	};
	
}]);
