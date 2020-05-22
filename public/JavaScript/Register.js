var BASE_URI="http://localhost:8080/dsaApp/auth"

$(document).ready(function(){
    console.log("iueputa")
    $("#register").click(function(){
        var username = $("#registerName").val();
        var mail = $("#registerMail").val();
        var password = $("#registerPassword").val();
        var confirm = $("#registerConfirm").val();
        if (password == confirm){
            var user = {"username": username, "mail":mail, "password": password};
            console.log(user);
            $.ajax({
                type: 'POST',
                url: Base_URI.concat("/register"),
                headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
                data: JSON.stringify(user),
                dataType: 'json',
                success: function (data) {
                    window.localStorage.setItem("username",username);
                    var url = "http://localhost:8080/home.html";
                    window.open(url, "_self");
                    alert(data)
                },
                error: function (e) {
                    // log error in browser
                    console.log(e.message);
                }
            });
        }
        else
            alert("Las contraseñas son distintas\n");
    });
});