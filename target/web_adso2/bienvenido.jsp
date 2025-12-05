<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Administración</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: linear-gradient(to right, #4e54c8, #8f94fb); margin: 0; padding: 0; }
        .container { background-color: white; margin: 50px auto; padding: 30px; border-radius: 10px; max-width: 800px; box-shadow: 0 0 15px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #4e54c8; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #f0f0f0; }
        .logout { text-align: right; margin-top: 20px; }
        .logout form input[type="submit"] { background-color: #e74c3c; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; }
        .logout form input[type="submit"]:hover { background-color: #c0392b; }
        .btn-eliminar { background-color:#e74c3c; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer; }
        .btn-eliminar:hover { background-color:#c0392b; }
        .btn-crear { background-color:#4CAF50; color:white; border:none; padding:8px 16px; border-radius:4px; cursor:pointer; margin-top:10px; }
        .btn-crear:hover { background-color:#388E3C; }
        .btn-editar { background-color:#3498db; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer; }
        .btn-editar:hover { background-color:#2980b9; }
        .error { color: red; text-align: center; margin-top: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Bienvenido, <%= session.getAttribute("user") %></h2>
        <h3>Lista de usuarios registrados</h3>
        <%
            List<Map<String, String>> usuarios = (List<Map<String, String>>) request.getAttribute("usuarios");
            if (usuarios == null || usuarios.isEmpty()) {
        %>
            <div class="error">No se encontraron usuarios en la base de datos.</div>
        <%
            } else {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Contraseña</th>
                <th>Acciones</th>
            </tr>
            <%
                for (Map<String, String> usuario : usuarios) {
            %>
            <tr>
                <td><%= usuario.get("id") %></td>
                <td><%= usuario.get("username") %></td>
                <td><%= usuario.get("password") %></td>
                <td>
                    <!-- Botón Editar -->
                    <form action="EditarUsuarioServlet" method="get" style="display:inline;">
                        <input type="hidden" name="id" value="<%= usuario.get("id") %>">
                        <input type="submit" value="✏️ Editar" class="btn-editar">
                    </form>

                    <!-- Botón Eliminar -->
                    <form action="EliminarUsuarioServlet" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= usuario.get("id") %>">
                        <input type="submit" value="🗑 Eliminar" class="btn-eliminar"
                               onclick="return confirm('¿Seguro que deseas eliminar este usuario?');">
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <% } %>

        <!-- Formulario para crear nuevo usuario -->
        <h3>Crear nuevo usuario</h3>
        <form action="CrearUsuarioServlet" method="post">
            <label>Usuario:</label>
            <input type="text" name="username" required>
            <label>Contraseña:</label>
            <input type="password" name="password" required>
            <input type="submit" value="➕ Crear" class="btn-crear">
        </form>

        <div class="logout">
            <form action="LogoutServlet" method="post">
                <input type="submit" value="Cerrar sesión">
            </form>
        </div>
    </div>
</body>
</html>
