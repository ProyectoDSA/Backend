$(document).ready(function(){
    console.log("hola");
    var token = sessionStorage.getItem("token")
    $.get("http://localhost:8080/dsaApp/game/objetos?token=" + token.toString(), function(data) {
        $.each(data,function (index,element) {
            console.log("hola1");
            var objeto = element.idObjeto;
            var cantidad = element.cantidad;
            console.log("22:",data);
            var insertion = "<tr><td>" + objeto + "</td><td>" + cantidad + "</td></tr>";
            $("#tabla_objeto tbody").append(insertion);
        })
    },"json");

    //EDITAR PERFIL

    $("#eliminar").click(function(){
        var token = sessionStorage.getItem("token")
        var BASE_URI="http://localhost:8080/dsaApp/user";
        $.ajax({
            type: 'DELETE',
            url: BASE_URI.concat("/delete?token=" + token.toString()),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            success: function () {
                console.log("hola1")
                window.sessionStorage.clear();
                var url = "http://localhost:8080/register.html";
                window.open(url, "_self");
                alert("Vuelve pronto :(")
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    })
});