$(document).ready(function(){
    var token = sessionStorage.getItem("token")
    $.get("http://localhost:8080/dsaApp/game/toppartidas?token=" + token.toString(), function(data) {
        $.each(data,function (index,element) {
            console.log("hola1");
            var puntos = element.puntos;
            var duracion = element.duracion;
            var  nivelMax= element.nivelMax;
            console.log("22:",data);
            var insertion = "<tr><td>" + puntos + "</td><td>" + duracion + "</td><td>" + nivelMax+ "</td></tr>";
            $("#tabla tbody").append(insertion);
        })
    },"json");
});