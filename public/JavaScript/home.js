var token = sessionStorage.getItem("token")
if (token == null){
    var url = "http://147.83.7.203:8080/login.html";
    window.open(url, "_self");
}
