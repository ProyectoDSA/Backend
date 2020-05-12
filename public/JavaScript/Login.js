var value1="";

function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function checkCookie() {
    var user = getCookie("name");
    console.log("user;", user);
    if (user != "") {
        alert("Hola: "+ user);
        value1=user;
        var queryString ="?"+value1;
        location.href = "http://localhost:8080/hola.html";
        window.location.href = "hola.html" + queryString;
    }
}

$(document).ready(function(){
    console.log("hola");
    $('#login').on('submit', function(e){
        user = $("#loginName").val();
        var myObj = {
            username: $("#loginName").val(),
            password: $("#loginPassword").val()
        };
        e.preventDefault();
        console.log("Chivato");
        checkCookie();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/dsaApp/usermanager/'+myObj.username+'/'+myObj.password,
            //data: JSON.stringify(myObj),
            success: function(data) {
                value1=user;
                setCookie("name", user, 30);
                location.href = "http://localhost:8080/hola.html";
                var queryString ="?"+value1;
                window.location.href = "hola.html" + queryString;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                if(xhr.status===500){
                    alert("Password not match");
                }
                else{
                    alert("User not found");
                }
            },
            contentType: "application/json",
            dataType: 'json'
        });
    });
});

