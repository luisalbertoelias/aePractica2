/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//El validate se aplica a un form. Se hace la relación mediante en "name"
//Se agrega function para que se ejecute cuando este cargado el formulario(DOM)
$(function(){
      
    $('#frmRole').validate({
        rules:{
            rolename:{
                //Reglas
                minlength:3,
                maxlength:20,
                required: true
            }
        },
        messages:{
            rolename:{
                minlength: "Debe tener más de 3 caracteres",
                maxlength: "Debe tener menos de 20 caracteres",
                required: "El nombre del rol es obligatorio"
            }
        },
        highlight: function(element){
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element){
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element){
            error.insertAfter(element.parent());
        },
        submitHandler: function(form){
            console.log("Al 1,000,000 viejon!!!");
            return true;
        }
    });
    
})  ;