var token = sessionStorage.getItem("token")
if (token == null){
    var url = "http://147.83.7.203:8080/login.html";
    window.open(url, "_self");
}
$(document).ready(function(){
    $.get("http://147.83.7.203:8080/dsaApp/game/toppartidas?token=" + token.toString(), function(data) {
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