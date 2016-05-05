Laboratory.controller('simulationController',['$scope', '$http','$routeParams', '$location', '$uibModal',
                                              function($scope,$http,$routeParams,$location,$uibModal){
	$scope.simulation={
			id:$routeParams.simulationId,
			name:"Simulacion de ejemplo",
			Circunscriptions:[]
	};
	
	$scope.newVotingIntent={party:{}};
	
	var reloadSimulation = function(){
		$http.get("/api/simulations",{params:{id:$routeParams.simulationId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.simulation = data;
			console.log($scope.simulation);
		});
	};
	
	var isJsonFile = function(file, onLoadCallback){
		if( file != undefined){
			fr = new FileReader();
			fr.readAsText(file[0]);
			fr.onloadend = onLoadCallback;
		}
	};
	
	$scope.$watchCollection("files",function(newValue){
		isJsonFile(newValue, function(e){
			try {
				var json = JSON.parse(e.target.result);
				//e.target.result es el string ya parseado y que es un json si JSON.parse no de excepción
				$scope.selectedCircumscription.localization = e.target.result;
				$scope.selectedCircumscription.localizationFilename = newValue[0].name;
				$scope.$apply();
			} catch(e){
				$("#topojson").val('');
				$scope.$apply();
				alert("It is not a JSON File");
				//si no fallta pues se elimina y listo
			}
		});
	});
	
	$scope.$watch("selectedCircumscription",function(newValue){
		$("#topojson").val('');
	});
	
	$scope.addCircumscription = function(){
		var circumscription = {name:"Nueva circunscripcion",polled:0,population:0,votingIntents:[]};
		$scope.simulation.Circunscriptions.push(circumscription);
		$scope.selectedCircumscription =circumscription;
	};

	$scope.deleteCircumscription = function(){
		var index = $scope.simulation.Circunscriptions.indexOf($scope.selectedCircumscription );
		if(index!=-1){
			$scope.simulation.Circunscriptions.splice(index,1);
			$scope.selectedCircumscription = undefined;
		}
	};
	
	$scope.deleteTopojson = function(selectedCircumscription){
		selectedCircumscription.localization = null;
		selectedCircumscription.localizationFilename = null;
		$("#topojson").val('');
	};

	$scope.insertVotingIntent = function(){
		if($scope.addVotingIntentForm.$valid){
			$scope.selectedCircumscription.votingIntents.push($scope.newVotingIntent);
			$scope.newVotingIntent={party:{}};
		}
	};

	$scope.deleteVotingIntent = function(votingIntent){
		var index = $scope.selectedCircumscription.votingIntents.indexOf(votingIntent);
		if(index!=-1){
			$scope.selectedCircumscription.votingIntents.splice(index,1);
		}
	};

	$scope.saveSimulation = function(){
		$http.put("/api/simulations",$scope.simulation).success(function(data,status){
			alert("Simulacion actualizada");
		});
	};

	reloadSimulation();
	
	$scope.animationsEnabled = true;
	$scope.createReport = function(){
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'modalView.html',
			controller: 'modalController'
		});
	
	
	modalInstance.result.then(
			function(resultCreated){
				$location.path("/projects/"+$routeParams.projectId+"/results/"+resultCreated.id);
			}
		);
	
	}
}]);


Laboratory.controller('modalController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                         function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){
	$scope.ok = function (simulation) {
		$http({
			method: 'POST',
			url: '/api/reports',
			data: JSON.stringify({
				name: $scope.nameReport,
			}),
			headers: {'Content-Type': 'application/json'},
			params: {simulation: $routeParams.simulationId}
			
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
