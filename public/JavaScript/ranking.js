$(document).ready(function(){
    var token = sessionStorage.getItem("token")
    $.get("http://147.83.7.203:8080/dsaApp/game/toppartidas", function(data) {
        console.log("hola1");
        var puntos = data.puntos;
        var duracion = data.duracion;
        var  nivelMax= data.nivelMax;
        console.log("22:",data);
        for (var i = 0; i < 5; i++) {
            var insertion = "</td></tr>"+puntos+"</td></tr>"+duracion+"</td></tr>"+nivelMax+"</td></tr>";
            $("#tabla tbody").append(insertion);}
    },"json");
});