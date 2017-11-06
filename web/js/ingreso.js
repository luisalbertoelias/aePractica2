/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var pin = getPin();
    if (pin === null) {
        paginaError();
    }
    getUser(pin);


});

function useAdmin(id, nombre, pin) {
    var titulo = document.getElementById("lbTitle");
    titulo.innerHTML = "<h1>" + nombre + "   (" + pin + ")</h1>";

    var botones = document.getElementById("btnBotones");
    botones.innerHTML = "<button type='button' class='btn btn-danger' onclick='mostrarActualizar(" + id + ",\"" + nombre + "\")'>Actualizar</button>";

    document.getElementById("tablaAdmin").style.display = "block";
    document.getElementById("tablaNormal").style.display = "none";
    generarTablaAdmin();
}
;

function useNormal(json) {
    var titulo = document.getElementById("lbTitle");
    titulo.innerHTML = "<h1>" + json.unombre + "   (" + json.pin + ")</h1>";

    var botones = document.getElementById("btnBotones");
    var cadena = "<button type='button' class='btn btn-danger' onclick='mostrarActualizar(" + json.uid + ",\"" + json.unombre + "\")'>Actualizar</button>";
    cadena += "<button type='button' class='btn btn-dafault' onclick='eliminar(" + json.uid + ")'>Eliminar</button>";
    botones.innerHTML = cadena;

    document.getElementById("tablaAdmin").style.display = "none";
    document.getElementById("tablaNormal").style.display = "none";
    if (json.permiso === true) {
        document.getElementById("tablaNormal").style.display = "block";
    }
    generarTablaNormal();
}
;

function mostrarActualizar(id, nombre) {
    $("#actualizarId").val(id);
    $("#actualizarNombre").val(nombre);

    $("#actualizarModal").modal("show");

}

function generarTablaNormal() {
    $("#tNormal").DataTable({
        ajax: {
            url: "ConsultarUsuario",
            dataSrc: function (json) {
                return $.parseJSON(json.detail);
            }
        },
        columns: [
            {
                data: "unombre"
            },
            {
                data: "fechaingreso"
            }
        ]
    });
}
;

function generarTablaAdmin() {
    $("#tAdmin").DataTable({
        ajax: {
            url: "ConsultarUsuario",
            dataSrc: function (json) {
                return $.parseJSON(json.detail);
            }
        },
        columns: [
            {
                data: "unombre"
            },
            {
                data: "fechaingreso"
            },
            {
                data: "fechaactualizacion"
            },
            {
                data: function (values) {
                    var boton = "";
                    if (values["permiso"] === true)
                    {
                        boton = "<button type='button' class='btn btn-danger' onclick='cambioPermiso(" + values["uid"] + "," + values['permiso'] + ")'>Desactivar</button>";
                    } else {
                        boton = "<button type='button' class='btn btn-danger' onclick='cambioPermiso(" + values["uid"] + "," + values['permiso'] + ")'>Activar</button>";
                    }
                    return boton;
                }
            }
        ]
    });
}
;

function cambioPermiso(id, vPermiso) {
    $.ajax({
        url: "CambioPermiso",
        type: "post",
        data: {
            uid: id,
            permiso: vPermiso
        }
    }).done(function () {
        $("#tAdmin").dataTable().api().ajax.reload(null, false);
    }).fail(function () {
        console.log("error en permiso");
    });
}
;

function paginaError() {
    var titulo = document.getElementById("lbTitle");
    var t = document.createElement("h1");
    t.createTextNode("ERROR");
    titulo.appendChild(t);

    var botones = document.getElementById("btnBotones");
    botones.appendChild(t);

    document.getElementById("tablaAdmin").style.display = "none";
    document.getElementById("tablaNormal").style.display = "none";
}
;

function getPin() {
    var paramstr = window.location.search.substr(1);
    var paramarr = paramstr.split("&");
    var params = {};

    for (var i = 0; i < paramarr.length; i++) {
        var tmparr = paramarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
    if (params['pin']) {
        return params['pin'];
    } else {
        return null;
    }
}
;

function getUser(pin) {
    $.ajax({
        url: "ConsultarPin",
        type: "post",
        data: {
            upin: pin
        }
    }).done(function (json) {
        var eljson = $.parseJSON(json.detail);
        if (eljson.admin === true) {
            useAdmin(eljson.uid, eljson.unombre, eljson.pin);
        } else {
            useNormal(eljson);
        }

    }).fail(function (json) {
        paginaError();
    });
}
;

function cerrarModal() {
    $("#actualizarId").val("");
    $("#actualizarNombre").val("nombre");
    $("#actualizarModal").modal("hide");
}
;

function actualizarUsuario(){
    $.ajax({
        url: "ActualizarUsuario",
        type: "post",
        data:{
            uid: $("#actualizarId").val(),
            unombre: $("#actualizarNombre").val()
        }
    }).done(function(json){
        $.growl.notice({
            title:"Exito",
            message: json.message
        });
        
        window.location.reload();
    }).fail(function(json){
        $.growl.notice({
            title:"Error",
            message: json.message
        });
    });
};