$(document).ready(function(){
    $('table tbody').empty();
    $.get("http://147.83.7.203:8080/dsaApp/game/ranking", function(Jugador, status) {
        for (var i = 0; i < 5; i++) {
            $("table").append("<tr><td>"+Jugador.idJugador+ "</td><td>"+Jugador.puntos+"</td></tr>");}
    });
});