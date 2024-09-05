$(document).ready(function() {
    let clave = "12345678"; // La clave utilizada para descifrar las contraseñas
    
    let datatable = $("#dataTable").DataTable({
        ajax: {
            url: 'validar',
            data: { ope: 2 },
           },
           
        columns: [
            {data: 'codiUsua'},
            {data: 'logiUsua'},
            {data: 'passUsua'},
            {data: 'nombUsua'}
        ],
        columnDefs: [
            {orderable: false, targets: [1]}
        ],
        pageLength: 5,
        lengthMenu: [5, 10, 25, 50, 100],
        language: {
            url: "https://cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"
        }
    });
});

function descifrar(encryptedMessage, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var decrypted = CryptoJS.DES.decrypt({
        ciphertext: CryptoJS.enc.Base64.parse(encryptedMessage)
    }, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
}
