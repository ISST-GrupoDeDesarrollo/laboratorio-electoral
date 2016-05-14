Laboratory.controller('publicController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	
	$scope.result = {};
	
	var getResult = function(){
		var reportId = $location.path();
		http({
			method: 'GET',
			url: '/api/publicReport',
			{params: {id: reportId} }
		}).success(function(data){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			console.log(data);
			updateConfigScope();
		}).eror(function(){
			
		});
	}
	getResult();	
	
	//Mostrar el mapa como en resultController
	//MAP LOGIC

	$scope.simulation={
			id:$routeParams.reportId,
			Report:[]
	};


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
			territory.properties.name = name;
			$scope.formed_json.features.push(territory);

			var party_representation_string = partyRepresentationString(congresses[n].parlamentaryGroup);
			var party_represntation_char_data = partyRepresentationCharData(congresses[n].parlamentaryGroup);


			$scope.dataForMap.push({
				"name": name,
				"localPopulation": congresses[n].localPopulation,
				"localVoters": congresses[n].localVoters,
				"parties": party_representation_string,
				"parties_data": party_represntation_char_data
			});
		}
		//console.log($scope.dataForMap);
	};

	/*
	*	Auxiliar method to create String for labels
	*/
	var partyRepresentationString = function(array){
		var string = "";
		for(var n = 0; n < array.length; n++){
			string += array[n].name +": " + array[n].deputies + " deputies<br>"
		}
		return string;
	};

	var partyRepresentationCharData = function(array){
		var party_data = [];
		for(var n = 0; n < array.length; n++){
			party_data.push([array[n].name, array[n].deputies])
		}
		return party_data;
	};

	/*
	*	Auxiliar method to update scope when downloading map
	*/
	var updateConfigScope = function(){
		$scope.config.title.text = $scope.result.name;
		createInfoStructure();
		console.log($scope.formed_json);
		$scope.config.series = [{
			animation: {
            	duration: 1000
            },
            name: "Voters",
			data: $scope.dataForMap,
			point:{
                	events:{
                    	click: function(){
                    				$scope.graphicCongress.series[0].data = [];
                    				$scope.graphicCongress.series[0].animation = {};
                    				$scope.$apply();

		                    		$scope.graphicCongress.series[0].data = this.parties_data;
		                    		$scope.graphicCongress.series[0].animation = {duration: 500};
									$scope.$apply();
                        }
                    }
                },
			mapData: $scope.formed_json,
			joinBy: ["name","name"],
			dataLabels: {
                    enabled: true,
                    color: '#332222',
                    format: '{point.name}'
            },
            tooltip: {
            	pointFormat: 'Population: ' + '{point.localPopulation}' + "<br>" + 'Voters: ' + '{point.localVoters}'+ '<br>'+ '{point.parties}'

            }
		}];
		

	};
/*
	var reloadResult = function(){
		$http.get("/api/reports",{params:{id:$routeParams.resultId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			console.log(data);
			updateConfigScope();
			
			
		});
	};*/

	/*
	* Map Initializer
	*/
	getResult();
	
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

    /*
	*	Chart Initializer
    */

    $scope.graphicCongress = {
       chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: 'Parliament<br>structure',
            align: 'center',
            verticalAlign: 'middle',
            y: 40
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        options:{
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -50,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }
                },
                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%']
            }
        }
        },
        series: [{
            type: 'pie',
            name: 'Parliament',
            innerSize: '50%',
            data: []
        }]
    };
}]);