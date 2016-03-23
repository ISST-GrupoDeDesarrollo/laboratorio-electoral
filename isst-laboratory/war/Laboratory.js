var Laboratory = angular.module('Laboratory', ['ui.bootstrap','ngRoute']); //Instaciamos la app y le pasamos un array con los nombres de las dependencias. Estos nombres vienen dados por las librerías

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
    }).when("/projects", {
        controller: "projectsController",
        templateUrl: "projects/index.html"
    }).when("/projects/:projectId", {
        controller: "projectController",
        templateUrl: "project/index.html"
    }).when("/projects/:projectId/simulations/:simulationId", {
        controller: "simulationController",
        templateUrl: "simulation/index.html"
    }).when("/projects/:projectId/results/:resultId", {
        controller: "resultController",
        templateUrl: "result/index.html"
    });
}]);