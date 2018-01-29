var app = angular.module("myApp", []);
app.controller('prodOpCtrl', function ($scope, $http) {

    var getAllProducts = function () {
        $http({
            method: "get",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/get-all'
        }).success(function (response) {
            $scope.products = response;
        })
    };

    $scope.getAllProducts = getAllProducts();

    getAllProducts();

    $scope.uploadProduct = function () {
        $http({
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/upload?' + "name=" + $scope.name + "&price="
            + $scope.price + "&description=" + $scope.description + "&username=" + $scope.username
        }).success(function () {
            alert("Product uploaded success!");
            $scope.name = "";
            $scope.price = "";
            $scope.username = "";
            $scope.description = "";
        })
    };

    $scope.searchByName = function () {
        $http({
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/search?' + "keyword=" + $scope.name + "&type=" + $scope.type
        }).success(function (response) {
            $scope.name = "";
            $scope.products = response;
            console.log(response);
        })
    };

    $scope.getByUser = function () {
        $http({
            method: "get",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/get-all'
        }).success(function (response) {
            $scope.products = response;
        })
    };

    $scope.deleteProduct = function () {
        $http({
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/delete?' + 'name=' + $scope.name + '&username=' + $scope.username
        }).success(function () {
            alert('Product ' + $scope.name + ' deleted successfully!')
        })
    };

    $scope.updateProduct = function () {
        $http({
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/update?' + "name=" + $scope.name + "&price="
            + $scope.price + "&description=" + $scope.description + "&username=" + $scope.username
        }).success(function () {
            alert('Product ' + $scope.name + ' updated successfully!')
        })
    };
    
    $scope.getProductsByPrice = function (min, max) {
        $http({
            method: "post",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, DELETE, PUT"
            },
            url: 'http://localhost:9000/get-by-price?' + "min=" + min + "&max=" + max
        }).success(function (response) {
            $scope.products = response;
        })
    };

    $scope.orderBy = function(x) {
        $scope.myOrderBy = x;
    }
});