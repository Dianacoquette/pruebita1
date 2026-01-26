let num1, num2, resultadoCorrecto;

function generarCaptcha() {
    num1 = Math.floor(Math.random() * 10) + 1;
    num2 = Math.floor(Math.random() * 10) + 1;
    resultadoCorrecto = num1 + num2;

    document.getElementById("captchaTexto").innerText =
        `¿Cuánto es ${num1} + ${num2}?`;
}

function validarFormulario() {
    let valido = true;

    let nombre = document.getElementById("nombre").value.trim();
    let email = document.getElementById("email").value.trim();
    let captcha = document.getElementById("captchaRespuesta").value.trim();

    document.getElementById("errorNombre").innerText = "";
    document.getElementById("errorEmail").innerText = "";
    document.getElementById("errorCaptcha").innerText = "";

    if (nombre === "") {
        document.getElementById("errorNombre").innerText =
            "El nombre es obligatorio";
        valido = false;
    }

    let regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regexEmail.test(email)) {
        document.getElementById("errorEmail").innerText =
            "Correo inválido";
        valido = false;
    }

    if (captcha === "" || parseInt(captcha) !== resultadoCorrecto) {
        document.getElementById("errorCaptcha").innerText =
            "Captcha incorrecto";
        valido = false;
        generarCaptcha(); // regenerar si falla
    }

    if (valido) {
        alert("Formulario enviado correctamente 🎉");
    }

    return valido;
}

window.onload = generarCaptcha;
