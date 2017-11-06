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
                    return fechanew;
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


function salario(sal) {
    var valor;
    if (sal.toString().indexOf(".") != -1) {
        var separacion = sal.toString().split(".");
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
        valor = "$" + sal + ".00";
    }
    return valor;
}
;


function fecha(fecha) {
    var split = fecha.split(" ");
    var fechaFinal = split[1].toString().replace(",", "") + "/";
    switch (split[0]) {
        case "Ene":
        case "ene":
            fechaFinal += "Enero";
            break;
        case "Feb":
        case "feb":
            fechaFinal += "Febrero";
            break;
        case "Mar":
        case "mar":
            fechaFinal += "Marzo";
            break;
        case "Abr":
        case "abr":
            fechaFinal += "Abri";
            break;
        case "May":
        case "may":
            fechaFinal += "Mayo";
            break;
        case "Jun":
        case "jun":
            fechaFinal += "Junio";
            break;
        case "Jul":
        case "jul":
            fechaFinal += "Julio";
            break;
        case "Ago":
        case "ago":
            fechaFinal += "Agosto";
            break;
        case "Sep":
        case "sep":
            fechaFinal += "Septiembre";
            break;
        case "Oct":
        case "oct":
            fechaFinal += "Octubre";
            break;
        case "Nov":
        case "nov":
            fechaFinal += "Noviembre";
            break;
        case "Dic":
        case "dic":
            fechaFinal += "Diciembre";
            break;

    }

    fechaFinal += "/" + split[2];
    return fechaFinal;
}
;