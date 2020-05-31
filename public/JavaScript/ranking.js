$(document).ready(function(){
    $("#login").click(function(){
        $('table tbody').empty();
        $.get("http://localhost:8080/dsaApp/usermanager/users/" + $("#user").val(), function(Object, status) {
            for (var i = 0; i < 1; i++) {
                $("table").append("<tr><td>"+Object.id+ "</td><td>"+Object.puntuacion+"</td></tr>");}
        });
    });
});