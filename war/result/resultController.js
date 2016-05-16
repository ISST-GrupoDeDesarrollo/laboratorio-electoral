Laboratory.controller('resultController', ['$scope', '$http','$routeParams', '$location','breadcrumbs', function($scope,$http,$routeParams,$location,breadcrumbs){
    breadcrumbs.setBreadcrumbs([breadcrumbs.createBreadcrumb("project", {projectId:$routeParams.projectId}),breadcrumbs.createBreadcrumb("report", {projectId:$routeParams.projectId,reportId:$routeParams.resultId})]);


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
            var voted_color = most_voted_color(congresses[n].parlamentaryGroup);


            //order local data
            party_represntation_char_data.sort(function (a,b) {
                console.log(a +  b)
            if(a.y < b.y) {
                return 1;
            } else if (a.y > b.y) {
                return -1;
            }
                return 0;
            });



			$scope.dataForMap.push({
				"name": name,
				"localPopulation": congresses[n].localPopulation,
				"localVoters": congresses[n].localVoters,
				"parties": party_representation_string,
				"parties_data": party_represntation_char_data,
                "color": voted_color
			});
		}
		$scope.globalDeputies = 0;
		for (var n = 0; n<$scope.result.globalCongress.parlamentaryGroup.length;n++){
            if( $scope.result.globalCongress.parlamentaryGroup[n].deputies > 0){
			 $scope.globalDeputies += $scope.result.globalCongress.parlamentaryGroup[n].deputies;
            }
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

    var most_voted_color = function(array){
        var string = "";
        var number = 0;
        var color = "blue";
        for(var n = 0; n < array.length; n++){
            if (array[n].deputies > number){
                number= array[n].deputies;
                string= array[n].name;
            }
        }

        color = getmycolor(string);

        return color;
    };

    var getmycolor = function(string){

        var color = "pinkl";
        if(string.toLowerCase() == "pp" || string.toLowerCase() == "partido popular"){
            color = "blue";
        }
        if(string.toLowerCase() == "podemos"){
            color = "#68228B";
        }
        if(string.toLowerCase() == "ciudadanos"){
            color = "orange";
        }
        if(string.toLowerCase() == "psoe" || string.toLowerCase() == "partido socialista" || string.toLowerCase() == "partido socialista obrero espanol"){
            color = "red";
        }
         if(string.toLowerCase() == "iu" || string.toLowerCase() == "izquierda unida"){
            color = "green";
        }
        return color;
    }

	var partyRepresentationCharData = function(array){
		var party_data = [];
		for(var n = 0; n < array.length; n++){
            if(array[n].deputies > 0 ){
			party_data.push({name: array[n].name, y: array[n].deputies,color:getmycolor(array[n].name)})
		  }
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
		console.log($scope.result);
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

                                    $scope.graphicCongress.series[0].color =['red','green','blue','yellow','orange'];
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
            if($scope.result.globalCongress.parlamentaryGroup[n].deputies >0){
			     congressSeries.push({ name: $scope.result.globalCongress.parlamentaryGroup[n].name,y: $scope.result.globalCongress.parlamentaryGroup[n].deputies,color:getmycolor($scope.result.globalCongress.parlamentaryGroup[n].name)});
            }
        }

         congressSeries.sort(function (a,b) {
                console.log(a +  b)
            if(a.y < b.y) {
                return 1;
            } else if (a.y > b.y) {
                return -1;
            }
                return 0;
            });

		$scope.graphicGlobalCongress.series[0].data = congressSeries; 
        $scope.WorldCongress = congressSeries;
	};

	var reloadResult = function(){
		$http.get("/api/reports",{params:{id:$routeParams.resultId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			//console.log(data);
			updateConfigScope();
			updateGlobalConfigScope();
			
			
		});
	};

	/*
	* Map Initializer
	*/
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

    /*
	*	Chart Initializer
    */

    $scope.graphicCongress = {
       chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false,
            marginBottom: 100
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
            series: {
             colorByPoint: true
            },
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
