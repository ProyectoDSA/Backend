var BASE_URI="http://147.83.7.203:8080/dsaApp/auth"

$(document).ready(function(){
    console.log("iueputa")
    $("#register").click(function(){
        var nombre = $("#registerName").val();
        var mail = $("#registerMail").val();
        var password = $("#registerPassword").val();
        var confirm = $("#registerConfirm").val();
        console.log("Carlos");
        if (password == confirm){
            var user = {"nombre": nombre, "mail":mail, "password": password, "confirm":confirm};
            console.log(user);
            $.ajax({
                type: 'POST',
                url: BASE_URI.concat("/register"),
                headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
                data: JSON.stringify(user),
                dataType: 'json',
                success: function (data) {
                    var token = data.token;
                    console.log(token);
                    window.sessionStorage.setItem("token", token)
                    var url = "http://147.83.7.203:8080/home.html";
                    window.open(url, "_self");
                    alert(token)
                },
                error: function (e) {
                    // log error in browser
                    console.log(e);
                }
            });
        }
        else
            alert("Las contraseñas son distintas\n");
    });
});