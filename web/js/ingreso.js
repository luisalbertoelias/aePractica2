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
                data: function(json){
                    var fechanew = fecha(json["fechaingreso"]);
                    return fecha;
                }
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
                data: function(json){
                    var fechanew = fecha(json["fechaingreso"]);
                    return fechanew;
                }
            },
            {
                data: function(json){
                    var fechanew = fecha(json["fechaactualizacion"]);
                    return fechanew;
                }
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
    t.innerHTML = "ERROR!!!";
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


function salario(valor) {
    var valor;
    if (valor.toString().indexOf(".") != -1) {
        var separacion = valor.toString().split(".");
        var entero = separacion[0];
        var decimales = separacion[1];
        valor = "$" + entero + ".";
        if (decimales.length === 1) {
            valor += decimales + "0";
        } else if (decimales.length > 2) {
            valor += decimales.toString().substring(0, 2);
        } else if (decimales.length === 2) {
            valor += decimales;
        }
    } else {
        valor = "$" + valor + ".00";
    }
    return valor;
}
;


function fecha(fecha) {
    var split = fecha.split(" ");
    var fechaFinal = split[1].toString().replace(",", "") + "/";
    switch (split[0]) {
        case "Ene":
            fechaFinal += "Enero";
            break;
        case "Feb":
            fechaFinal += "Febrero";
            break;
        case "Mar":
            fechaFinal += "Marzo";
            break;
        case "Abr":
            fechaFinal += "Abri";
            break;
        case "May":
            fechaFinal += "Mayo";
            break;
        case "Jun":
            fechaFinal += "Junio";
            break;
        case "Jul":
            fechaFinal += "Julio";
            break;
        case "Ago":
            fechaFinal += "Agosto";
            break;
        case "Sep":
            fechaFinal += "Septiembre";
            break;
        case "Oct":
            fechaFinal += "Octubre";
            break;
        case "Nov":
            fechaFinal += "Noviembre";
            break;
        case "Dic":
            fechaFinal += "Diciembre";
            break;

    }

    fechaFinal += "/" + split[2];
    return fechaFinal;
}
;