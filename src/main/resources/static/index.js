function cargaMaterias() {
    const xhttp = new XMLHttpRequest();

    xhttp.open("GET", "http://localhost:8080/api/v1/materias/all");
    xhttp.send();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            var trHTML = "";
            for (let object of objects) {
                trHTML += "<tr>";
                trHTML += "<td>" + object["id"] + "</td>";
                trHTML += "<td>" + object["nombre"] + "</td>";
                trHTML += "<td>" + object["creditos"] + "</td>";
                trHTML +=
                    '<td><button type="button" class="btn btn-outline-secondary" onclick="cargaCursos(' + object["id"] + ')">Ver cursos</button>';
                trHTML += "</tr>";
            }
            document.getElementById("materiasTable").innerHTML = trHTML;
        }
    }
}

function edicionMateria() {
    Swal.fire({
        title: "Editar Materia",
        html:
            '<input id="id" type="hidden">' +
            '<input id="nombre" class="swal2-input"  placeholder="Nombre">' +
            '<input id="creditos" class="swal2-input" placeholder="Creditos">',
        focusConfirm: false,
        preConfirm: () => {
            creaMateria();
        },
    });
}

function creaMateria() {
    const nombre = document.getElementById("nombre").value;
    const creditos = document.getElementById("creditos").value;

    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/api/v1/materias/add");
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            nombre: nombre,
            creditos: creditos
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            Swal.fire(objects["message"]);
            cargaMaterias();
        }
    };
}

function borrarMateria(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/api/v1/materias/delete/" + id);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.status == 204) {
            Swal.fire("Materia Borrada");
            cargaMaterias();
        }
    };
}

function cargaCursos() {
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/api/v1/cursos/all");
    xhttp.send();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            var trHTML = "";
            for (let object of objects) {
                trHTML += "<tr>";
                trHTML += "<td>" + object["materiaId"] + "</td>";
                trHTML += "<td>" + object["profesorId"] + "</td>";
                trHTML += "<td>" + object["numero"] + "</td>";
                trHTML += "<td>" + object["fechaInicio"] + "</td>";
                trHTML += "<td>" + object["fechaFin"] + "</td>";
                trHTML +=
                    '<td><button type="button" class="btn btn-outline-secondary" onclick="cargaPersonas(' + object["id"] + ')">Ver Personas</button>';
                trHTML += "</tr>";
            }
            document.getElementById("cursosTable").innerHTML = trHTML;
        }
    }
}

function edicionCurso() {
    Swal.fire({
        title: "Editar Curso",
        html:
            '<input id="id" type="hidden">' +
            '<input id="numero" class="swal2-input"  placeholder="Numero">' +
            '<input id="fechaInicio" class="swal2-input" placeholder="Fecha_inicio">' +
        '<input id="fechaFin" class="swal2-input" placeholder="Fecha_fin">',
        focusConfirm: false,
        preConfirm: () => {
            creaCurso();
        },
    });
}

function creaCurso() {
    const materiaId = document.getElementById("materiaId").value;
    const profesorId = document.getElementById("profesorId").value;
    const numero = document.getElementById("numero").value;
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;

    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/api/v1/cursos/add");
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            materiaId: materiaId,
            profesorId: profesorId,
            numero: numero,
            fechaInicio: fechaInicio,
            fechaFin: fechaFin
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            Swal.fire(objects["message"]);
            cargaCursos();
        }
    };
}

function borrarCurso(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/api/v1/cursos/delete/" + id);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.status == 204) {
            Swal.fire("Curso Borrado");
            cargaCursos();
        }
    };
}

function cargaPersonas() {
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/api/v1/personas/rol/E");
    xhttp.send();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            var trHTML = "";
            for (let object of objects) {
                trHTML += "<tr>";
                trHTML += "<td>" + object["id"] + "</td>";
                trHTML += "<td>" + object["nombre"] + "</td>";
                trHTML += "<td>" + object["apellido"] + "</td>";
                trHTML += "<td>" + object["correo"] + "</td>";
                trHTML += "<td>" + object["rol"] + "</td>";
                trHTML += '<td><button type="button" class="btn btn-outline-secondary" onclick="cargaNotas(' + object["id"] + ')">Ver Notas</button></td>';
                trHTML += "</tr>";
            }
            document.getElementById("personasTable").innerHTML = trHTML;
        }
    }
}


function edicionPersonas() {
    Swal.fire({
        title: "Editar Persona",
        html:
            '<input id="id" type="hidden">' +
            '<input id="nombre" class="swal2-input"  placeholder="Nombre">' +
            '<input id="apellido" class="swal2-input" placeholder="Apellido">' +
            '<input id="correo" class="swal2-input" placeholder="Correo">' +
            '<input id="rol" class="swal2-input" placeholder="Rol">',
        focusConfirm: false,
        preConfirm: () => {
            creaPersona();
        },
    });
}

function creaPersona() {
    const id = document.getElementById("id").value;
    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const correo = document.getElementById("correo").value;
    const rol = document.getElementById("rol").value;

    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/api/v1/personas/add");
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
            nombre: nombre,
            apellido: apellido,
            correo: correo,
            rol: rol
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            Swal.fire(objects["message"]);
            cargaPersonas();
        }
    };
}

function borrarPersona(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/api/v1/personas/delete/" + id);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.status == 204) {
            Swal.fire("Persona Borrada");
            cargaCursos();
        }
    };
}

function cargaNotas() {
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/api/v1/notas/all");
    xhttp.send();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            var trHTML = "";
            for (let object of objects) {
                trHTML += "<tr>";
                trHTML += "<td>" + object["id"] + "</td>";
                trHTML += "<td>" + object["observacion"] + "</td>";
                trHTML += "<td>" + object["valor"] + "</td>";
                trHTML += "<td>" + object["porcentaje"] + "</td>";
            }
            document.getElementById("notasTable").innerHTML = trHTML;
        }
    }
}

function edicionNotas() {
    Swal.fire({
        title: "Editar Nota",
        html:
            '<input id="id" type="hidden">' +
            '<input id="observacion" class="swal2-input"  placeholder="Observacion">' +
            '<input id="valor" class="swal2-input" placeholder="Valor">' +
            '<input id="porcentaje" class="swal2-input" placeholder="Porcentaje">',
        focusConfirm: false,
        preConfirm: () => {
            creaNota();
        },
    });
}

function creaNota() {
    const id = document.getElementById("id").value;
    const observacion = document.getElementById("observacion").value;
    const valor = document.getElementById("valor").value;
    const porcentaje = document.getElementById("porcentaje").value;

    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/api/v1/notas/add");
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
            observacion: observacion,
            valor: valor,
            porcentaje: porcentaje
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            Swal.fire(objects["message"]);
            cargaNotas();
        }
    };
}

function borrarNotas(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/api/v1/notas/delete/" + id);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
        JSON.stringify({
            id: id,
        })
    );
    xhttp.onreadystatechange = function () {
        if (this.status == 204) {
            Swal.fire("Persona Borrada");
            cargaCursos();
        }
    };
}


