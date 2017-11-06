/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    
    $("#frmPrueba").validate({
        rules:{
            txtPin:{
                required:true,
                minlength:2
            }
        },
        messages:{
            txtPin:{
                required:"Es requerido el PIN",
                minlength:"Cantidad de caracteres es 2"
            }
        },
        highlight: function(element){
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element){
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement:'span',
        errorClass: 'help-block',
        errorPlacement: function(error,element){
            error.insertAfter(element.parent());
        },
        submitHandler: function(form){
            envio();
            return false;
        }
    });


});

function abrirRegistro(){
    window.location.replace("registro.html");
};

function envio() {
    var url = 'ingreso.html?pin=' + $("#txtPin").val();
    window.location.replace(url);
    return false;
};