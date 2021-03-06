var Laboratory = angular.module('Laboratory', ['ui.bootstrap','ngRoute','highcharts-ng','ui.multiselect','ngSanitize']); //Instaciamos la app y le pasamos un array con los nombres de las dependencias. Estos nombres vienen dados por las librerías

Laboratory.config(['$routeProvider',function ($routeProvider) { //Configuro el proveedor de rutas de la app. Sacado de la documentación de ngRoute
    $routeProvider.when("/", {
        controller: "welcomeController",
        templateUrl: "welcomePage/index.html"
    }).when("/login", {
        controller: "loginController",
        templateUrl: "login/index.html"
    }).when("/register", {
        controller: "registerController",
        templateUrl: "register/index.html"
    }).when("/workgroups", {
        controller: "workgroupsController",
        templateUrl: "workgroups/index.html"
    }).when("/projects", {
        controller: "projectsController",
        templateUrl: "projects/index.html"
    }).when("/profile", {
        controller: "profileController",
        templateUrl: "profile/index.html"
    }).when("/projects/:projectId", {
        controller: "projectController",
        templateUrl: "project/index.html"
    }).when("/projects/:projectId/simulations/:simulationId", {
        controller: "simulationController",
        templateUrl: "simulation/index.html"
    }).when("/projects/:projectId/results/:resultId", {
        controller: "resultController",
        templateUrl: "result/index.html"
    }).when("/public/:publicReportId",{
    	controller: "publicController",
        templateUrl: "public/index.html"
    }).otherwise({redirectTo: '/'});
}]);

Laboratory.run(['$rootScope','$http',function($rootScope,$http){
    $rootScope.cleanObjectFromDatabase = function(obj){
        for(attr in obj){
            if(attr === "jdoDetachedState"){
                obj[attr] = undefined;
            }
            var child = obj[attr];
            if(child!==null && child!==undefined){
                if(child instanceof Array){
                    for (var i = child.length - 1; i >= 0; i--) {
                        var elem = child[i];
                        $rootScope.cleanObjectFromDatabase(elem);
                    }
                }else{
                    if(typeof child === 'object'){
                        $rootScope.cleanObjectFromDatabase(child);
                    }
                }
            }
        }
    }

    var loadAppUser = function(){
         $http.get("/api/login").success(function(user){
            $rootScope.appUser = user;
            $rootScope.$broadcast("checkAuth");
        });
    }
   
    $rootScope.$on("loginChanged",function(){
        loadAppUser();
    });

   $rootScope.$on('$routeChangeStart', function() { 
        loadAppUser();
    });
   $rootScope.$on('$routeChangeSuccess', function() { 
        $rootScope.breadcrumbs = undefined;
    });
}]);

Laboratory.service('breadcrumbs', ['$rootScope','$http','$location',function ($rootScope,$http,$location) {
    
    $rootScope.breadcrumbs = [];
    $rootScope.navTo = function (breadcrumb) {
        $location.path(breadcrumb.path);
    }
    
    var createBreadcrumb = function (identifier, pathArgs) {
        var breadcrumb = {};
        var projectId = pathArgs.projectId;
        var simulationId = pathArgs.simulationId;
        var reportId = pathArgs.reportId;
        switch (identifier) {
            case "projects":
            breadcrumb.name = "Projects";
            breadcrumb.path = "/projects";
            break;
            case "project":
            breadcrumb.name = projectId;
            breadcrumb.path = "/projects/" + projectId;
            $http.get("/api/projects",{params:{id:projectId}}).success(function (data, status) {
                breadcrumb.name = data.name;
            });
            break;
            case "simulation":
            breadcrumb.name = simulationId;
            breadcrumb.path = "/projects/" + projectId + "/simulations/" + simulationId;
            $http.get("/api/simulations",{params:{id:simulationId}}).success(function(data,status){
                breadcrumb.name = data.name;
            });
            break;
            case "report":
            breadcrumb.name = "Interferences";
            breadcrumb.path = "/projects/" + projectId + "/reports/" + reportId;
            $http.get("/api/reports",{params:{id:reportId}}).success(function(data,status){
                breadcrumb.name = data.name;
            });
            break;
          
        }
        return breadcrumb;
    }
    var setBreadcrumbs = function (breadcrumbs) {
        $rootScope.breadcrumbs = breadcrumbs;
    }
    return {
        createBreadcrumb: createBreadcrumb,
        setBreadcrumbs:setBreadcrumbs
    }
}]);

Laboratory.directive('fileInput', ['$parse', function($parse){
    return {
        restrict: 'A',
        link: function(scope, element, attrs){
            element.bind('change', function(){
                $parse(attrs.fileInput).assign(scope,element[0].files)
                scope.$apply();
            });
        }
    };
}]);

Laboratory.filter('exclude', function() {
    return function(input, exclude, prop) {
        if (!angular.isArray(input))
            return input;

        if (!angular.isArray(exclude)){
            var item = exclude;
            if(item){
            exclude = [item];}else{
                exclude=[];
            }
        }

        if (prop) {
            exclude = exclude.map(function byProp(item) {
                return item[prop];
            });
        }

        return input.filter(function byExclude(item) {
            return exclude.indexOf(prop ? item[prop] : item) === -1;
        });
    };
});