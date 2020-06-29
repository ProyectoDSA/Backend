var token = sessionStorage.getItem("token")
if (token == null){
    var url = "http://147.83.7.203:8080/login.html";
    window.open(url, "_self");
}
$(document).ready(function(){
    console.log("hola");
    $.get("http://147.83.7.203:8080/dsaApp/game/objetos?token=" + token.toString(), function(data) {
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
    $.get("http://147.83.7.203:8080/dsaApp/user/user?token=" + token.toString(), function(data) {
        console.log("Porque no vas...")
         var nombre = data.nombre;
         var mail = data.mail;
         console.log("1:", nombre);
         console.log("2:", mail);
         $("#EditarNombre").val(nombre);
         $("#EditarCorreo").val(mail);
    },"json");
    $("#editar").click(function () {
        var BASE_URI_UPDATE="http://147.83.7.203:8080/dsaApp/user/update";
        console.log("3434");
        var nom = $("#EditarNombre").val();
        var ma =  $("#EditarCorreo").val();
        var pass = $("#EditarPassword").val();
        if (pass == "")
            pass = null;
        var editar = {"idUser": token, "nombre": nom, "mail": ma, "password": pass, "status": "active"};
        console.log(editar);
        $.ajax({
            type: 'POST',
            url: BASE_URI_UPDATE,
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            data: JSON.stringify(editar),
            success: function () {
                console.log("BIEEEEEEN")
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    })

    //Eliminar

    $("#eliminar").click(function(){
        var BASE_URI="http://147.83.7.203:8080/dsaApp/user";
        $.ajax({
            type: 'DELETE',
            url: BASE_URI.concat("/delete?token=" + token.toString()),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            success: function () {
                console.log("hola1")
                window.sessionStorage.clear();
                var url = "http://147.83.7.203:8080/register.html";
                window.open(url, "_self");
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    })
});