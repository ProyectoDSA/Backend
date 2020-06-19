var BASE_URI="http://localhost:8080/dsaApp/user";
var token = sessionStorage.getItem("token")
if (token == null){
    var url = "http://localhost:8080/login.html";
    window.open(url, "_self");
}

$(document).ready(function() {
    $("#exitButton").click(function () {
        $.ajax({
            type: 'DELETE',
            url: BASE_URI.concat("/signout?token=" + token.toString()),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            success: function () {
                console.log("hola1")
                window.sessionStorage.clear();
                var url = "http://localhost:8080/login.html";
                window.open(url, "_self");
                alert("Vuelve pronto :(")
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });
});