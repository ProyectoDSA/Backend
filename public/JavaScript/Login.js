var BASE_URI="http://147.83.7.203:8080/dsaApp/auth";

$(document).ready(function() {
    console.log("hola");
    $('#login').click(function () {
        var nombre = $("#loginName").val();
        var password = $("#loginPassword").val();
        var user = {"nombre": nombre, "password": password};
        console.log(user);

        $.ajax({
            type: 'POST',
            url: BASE_URI.concat("/login"),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            data: JSON.stringify(user),
            dataType: 'json',
            success: function (data) {
                console.log("hola1")
                var token = data.token;
                console.log(token);
                window.sessionStorage.setItem("token", token)
                var url = "http://147.83.7.203:8080/home.html";
                window.open(url, "_self");
                alert(token)
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });
})
