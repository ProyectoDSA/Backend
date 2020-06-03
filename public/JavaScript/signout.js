var BASE_URI="http://localhost:8080/dsaApp/auth";

$(document).ready(function() {
    $('#exitButton').click(function () {
        var token = sessionStorage.getItem("token")

        $.ajax({
            type: 'DELETE',
            url: BASE_URI.concat("/delete/"+token.toString()),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            success: function () {
                console.log("hola1")
                window.sessionStorage.clear();
                var url = "http://localhost:8080/login.html";
                window.open(url, "_self");
                alert("Dw")
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });
});