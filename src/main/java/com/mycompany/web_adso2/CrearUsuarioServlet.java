package com.mycompany.web_adso2;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt; // Importar BCrypt

@WebServlet(name = "CrearUsuarioServlet", urlPatterns = {"/CrearUsuarioServlet"})
public class CrearUsuarioServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/web_adso2";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Generar hash seguro con salt automático
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO usuarios (username, password) VALUES (?, ?)")) {
                ps.setString(1, username);
                ps.setString(2, hashedPassword); // Guardar el hash, no el texto plano
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirige de nuevo a la lista
        response.sendRedirect("ListaUsuariosServlet");
    }
}
