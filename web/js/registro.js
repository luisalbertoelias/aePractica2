/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#frmRegistro").validate({
        rules: {
            tnr: {
                required: true
            },
            tpr: {
                required: true
            }
        },
        messages: {
            tnr: {
                required: "Es requerido el Nombre"
            },
            tpr: {
                required: "Es requerido el PIN"
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        },
        submitHandler: function (form) {
            guardar();
            return false;
        }
    });
});


function guardar() {
    var admin = document.getElementsByName("admin");
    var resultado;
    for (var i = 0; i < admin.length; i++)
    {
        if (admin[i].checked)
            resultado = admin[i].value;
    }

    $.ajax({
        url: "GuardarUsuario",
        type: "post",
        data: {
            unombre: $("#tnr").val(),
            pin: $("#tpr").val(),
            admin: resultado
        }
    }).done(function () {
        window.location.replace("index.html");
    }).fail(function () {
        console.log("error en permiso");
    });
}
;