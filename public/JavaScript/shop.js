var BASE_URI="http://147.83.7.203:8080/dsaApp/game";

$(document).ready(function() {
    //var idJugador = JSON.parse(sessionStorage.getItem("token")).token;
    var idJugador = sessionStorage.getItem("token");
    console.log(idJugador);
    var cantidad1 = $("#numero1").val();
    var cantidad2 = $("#numero2").val();
    var cantidad3 = $("#numero3").val();
    var cantidad4 = $("#numero4").val();
    var cantidad5 = $("#numero5").val();
    var cantidad6 = $("#numero6").val();
    $('#s1').click(function (){
        cantidad1 + 1;
    })
    $('#r1').click(function () {
        if(cantidad1 != 1) cantidad1 - 1;

    })
    $('#s2').click(function (){
        cantidad2 + 1;
    })
    $('#r2').click(function () {
        if(cantidad2 != 1) cantidad2 - 1;

    })
    $('#s3').click(function (){
        cantidad3 + 1;
    })
    $('#r3').click(function () {
        if(cantidad3 != 1) cantidad3 - 1;

    })
    $('#s4').click(function (){
        cantidad4 + 1;
    })
    $('#r4').click(function () {
        if(cantidad4 != 1) cantidad4 - 1;

    })
    $('#s5').click(function (){
        cantidad5 + 1;
    })
    $('#r5').click(function () {
        if(cantidad5 != 1) cantidad5 - 1;

    })
    $('#s6').click(function (){
        cantidad6 + 1;
    })
    $('#r6').click(function () {
        if(cantidad6 != 1) cantidad6 - 1;

    })
    $('#des1').click(function () {
        var inventario = {"idObjeto": 1, "cantidad": 1, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });
    $('#des2').click(function () {
        var inventario = {"idObjeto": 2, "cantidad": 1, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });
    $('#des3').click(function () {
        var inventario = {"idObjeto": 3, "cantidad": 1, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });
    $('#masc1').click(function () {
        var inventario = {"idObjeto": 4, "cantidad": 1, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });
    $('#masc2').click(function () {
        var inventario = {"idObjeto": 5, "cantidad": 1, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });
    $('#jabon').click(function () {
        var inventario = {"idObjeto": 6, "cantidad": 2, "idJugador": idJugador};
        console.log(inventario);
        compraObjeto(inventario);
    });

    function compraObjeto(inventario) {
        console.log("comprando objeto",inventario);
        $.ajax({
            type: 'PUT',
            url: BASE_URI.concat("/compra"),
            headers: {'content-type': 'application/json', "x-kii-appid": "XXXXX", "x-kii-appkey": "XXXXX"},
            data: JSON.stringify(inventario),
            dataType: 'json',
            success: function () {
                alert("Objeto comprado con exito")
            },
            error: function (e) {
                console.log(e.message);
                alert("Objeto comprado con exito")
            }
        });
    }
});
