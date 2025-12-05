<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
</head>
<body>
    <h2>Editar Usuario</h2>
    <form action="EditarUsuarioServlet" method="post">
        <input type="hidden" name="id" value="${id}">
        
        <label>Usuario:</label>
        <input type="text" name="username" value="${username}" required><br>
        
        <label>Contraseña (dejar vacío si no cambia):</label>
        <input type="password" name="password" value=""><br>
        
        <input type="submit" value="Guardar cambios">
    </form>
</body>
</html>
