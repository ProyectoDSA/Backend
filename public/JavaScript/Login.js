var Base_URI="http://147.83.7.203:8080/dsaApp/";

$(document).ready(function(){
    console.log("hola");
    $('#login').click(function(){
         var username= $("#loginName").val();
         var password= $("#loginPassword").val();
         var user = {"username": username, "password": password};
         console.log(user);

        $.ajax({
            type: 'POST',
            url: Base_URI.concat("usermanager/"+username+'/'+password),
            headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
            data: JSON.stringify(user),
            dataType: 'json',
            success: function(data) {
                console.log("hola1")
                window.localStorage.setItem("name", username)
                var url = "http://147.83.7.203:8080/hola.html";
                window.open(url, "_self");
                alert(data)
            },
            error: function (e) {
                console.log(e.message);
        }
        });
    });
});

