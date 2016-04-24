Laboratory.controller('simulationController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
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
}]);
