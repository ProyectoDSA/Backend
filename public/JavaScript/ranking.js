$(document).ready(function(){
    $("#login").click(function(){
        $('table tbody').empty();
        $.get("http://147.83.7.203:8080/dsaApp/usermanager/users/" + $("#user").val(), function(Object, status) {
            for (var i = 0; i < 1; i++) {
                $("table").append("<tr><td>"+Object.id+ "</td><td>"+Object.puntuacion+"</td></tr>");}
        });
    });
});