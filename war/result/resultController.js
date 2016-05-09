Laboratory.controller('resultController', ['$scope', '$http','$routeParams', '$location','breadcrumbs', function($scope,$http,$routeParams,$location,breadcrumbs){
    breadcrumbs.setBreadcrumbs([breadcrumbs.createBreadcrumb("project", {projectId:$routeParams.projectId}),breadcrumbs.createBreadcrumb("report", {projectId:$routeParams.projectId,reportId:$routeParams.resultId})]);

	$scope.simulation={
			id:$routeParams.reportId,
			Report:[]
	};

	$scope.result = {};

	/*
	*	Merge GEOJson and create labels and colors properly
	*/
	var createInfoStructure = function(){
		var congresses = $scope.result.congress;
		$scope.dataForMap = [];
		$scope.formed_json = {"type": "FeatureCollection","features":[]};
		for (var n = 0; n < congresses.length; n++){
			var name = congresses[n].locationName;
			$scope.result.territories[name].name = name;
			var territory= JSON.parse($scope.result.territories[name]);
			$scope.formed_json.features.push(territory);
			
			$scope.dataForMap.push({
				"name": name,
				"value": congresses[n].localVoters
			});
		}
		console.log($scope.dataForMap);
	};

	var updateConfigScope = function(){
		$scope.config.title.text = $scope.result.name;
		createInfoStructure();
		console.log($scope.formed_json);
		$scope.config.series = [{
			animation: {
            	duration: 1000
            },
			data: $scope.dataForMap,
			mapData: $scope.formed_json,
			joinBy: ["name","name"],
			dataLabels: {
                    enabled: true,
                    color: '#222222',
                    format: '{point.name}'
            },
            tooltip: {
            	pointFormat: '{point.name}: {point.value}/km²'
            }
		}];
	};

	var reloadResult = function(){
		$http.get("/api/reports",{params:{id:$routeParams.resultId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			console.log(data);
			updateConfigScope();
			
			
		});
	};

	$scope.map = {};

	reloadResult();
	
	$scope.config = {
            options: {
                legend: {
                    enabled: false
                }
            },
            chartType: 'map',
            title: {
                text: $scope.titulo
            }
        };

}]);
