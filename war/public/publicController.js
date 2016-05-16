Laboratory.controller('publicController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	
		
	
	//Mostrar el mapa como en resultController
	//MAP LOGIC


	$scope.simulation={
			id:$routeParams.reportId,
			Report:[]
	};

	$scope.result = {};

	/*
	*	Merge GEOJson and create labels and colors properly
	*/
	var localInfoStructure = function(){
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
		$scope.globalDeputies = 0;
		for (var n = 0; n<$scope.result.globalCongress.parlamentaryGroup.length;n++){
			$scope.globalDeputies += $scope.result.globalCongress.parlamentaryGroup[n].deputies;
		}
		$scope.participation = (($scope.result.globalCongress.localVoters/$scope.result.globalCongress.localPopulation)*100).toFixed(2);
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
		console.log(party_data);
		return party_data;
	};

	/*
	*	Auxiliar method to update scope when downloading map
	*/
	var updateConfigScope = function(){
		$scope.config.title.text = "";
		localInfoStructure();
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

	var updateGlobalConfigScope = function(){
		var congressSeries = [];
		for (var n = 0; n<$scope.result.globalCongress.parlamentaryGroup.length;n++){
			congressSeries.push([$scope.result.globalCongress.parlamentaryGroup[n].name,$scope.result.globalCongress.parlamentaryGroup[n].deputies]);
		}

		$scope.graphicGlobalCongress.series[0].data = congressSeries; 
	};
	
	var getResult = function(){
		var reportId = $routeParams.publicReportId;
		$http({
			method: 'GET',
			url: '/api/publicReport',
			params: {id: reportId} 
		}).success(function(data){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			updateConfigScope();
			updateGlobalConfigScope();
		}).error(function(){
			
		});
	}
	getResult();

	/*
	* Map Initializer
	*/
	
	
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
    

    $scope.graphicGlobalCongress =  {
       chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false,
            margin: [0, 0, 0, 0],
            spacingTop: 0,
            spacingBottom: 0,
            spacingLeft: 0,
            spacingRight: 0
        },
        title: {
            text: '',
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
            	size:"100%",
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