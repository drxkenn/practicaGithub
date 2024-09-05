$(document).ready(function () {
    $("#btnLogin").click(function () {
        let logi = $("#txtLogin").val();
        let pass = $("#txtPassword").val();

        let clave = "12345678";

        let clientecifrado = cifrar(logi, clave);
        let passcifrado = cifrar(pass, clave);
        let parametros = { ope: 1, logiUsua: clientecifrado, passUsua: passcifrado };

        console.log("Usuario cifrado: " + clientecifrado);
        console.log("Contraseña cifrada: " + passcifrado);

        $.getJSON("validar", parametros, function (data) {
            console.log(data);
            if (data.resultado === "ok") {
                window.location.href = "principal.html";
            } else {
                alert("Error en la autenticación");
            }
        });
    });
});

function cifrar(message, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}
