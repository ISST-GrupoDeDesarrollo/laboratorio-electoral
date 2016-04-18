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

	$scope.addCircumscription = function(){
		var circumscription = {name:"Nueva circunscripción",polled:0,population:0,votingIntents:[]};
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
		$http.post("/api/simulations",$scope.simulation).success(function(data,status){
			console.log("Saved simulation!!! :D");
		});
	};

	reloadSimulation();
}]);
