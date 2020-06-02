var BASE_URI="http://localhost:8080/dsaApp/auth";

$(document).ready(function() {
    console.log("holi caramierda")
    $('#exitButton').click(function () {
        console.log("holi caracoli")
        var token = sessionStorage.getItem("token")

        $.ajax({
            type: 'DELETE',
            url: BASE_URI.concat("/delete/"+token.toString()),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            dataType: 'json',
            success: function (data) {
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
})