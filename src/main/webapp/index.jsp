<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #4e54c8, #8f94fb);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background-color: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            width: 350px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #4e54c8;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }
        .success {
            color: green;
            text-align: center;
            margin-bottom: 10px;
        }
        .actions {
            margin-top: 20px;
            text-align: center;
        }
        input[type="submit"] {
            background-color: #4e54c8;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #3b3fc1;
        }
        .links {
            margin-top: 15px;
            text-align: center;
            font-size: 0.9em;
        }
        .links a {
            color: #4e54c8;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Iniciar Sesión</h2>

        <%-- Mensaje de error si login falla --%>
        <% if (request.getParameter("error") != null) { %>
            <div class="error">Usuario o contraseña incorrectos. Inténtalo nuevamente por favor.</div>
        <% } %>

        <%-- Mensaje de éxito si se cerró sesión --%>
        <% if (request.getParameter("logout") != null) { %>
            <div class="success">Sesión cerrada correctamente.</div>
        <% } %>

        <form action="LoginServlet" method="post">
            <label for="username">Usuario</label>
            <input type="text" name="username" id="username" required>

            <label for="password">Contraseña</label>
            <input type="password" name="password" id="password" required>

            <div class="actions">
                <input type="submit" value="Iniciar sesión">
            </div>
        </form>
        <div class="links">
            <a href="#">¿Has olvidado tu clave?</a> / <a href="#">¿No te has registrado?</a>
        </div>
    </div>
</body>
</html>
