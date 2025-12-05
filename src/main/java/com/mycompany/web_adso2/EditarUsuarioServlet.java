package com.mycompany.web_adso2;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt; // Importar BCrypt

@WebServlet(name = "EditarUsuarioServlet", urlPatterns = {"/EditarUsuarioServlet"})
public class EditarUsuarioServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/web_adso2";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String username = "";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios WHERE id=?")) {
                ps.setInt(1, Integer.parseInt(id));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    username = rs.getString("username");
                    // No mostramos la contraseña real, solo dejamos el campo vacío
                    password = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("id", id);
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

                if (password == null || password.isBlank()) {
                    // Si no se cambia la contraseña, solo actualizamos el username
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE usuarios SET username=? WHERE id=?")) {
                        ps.setString(1, username);
                        ps.setInt(2, Integer.parseInt(id));
                        ps.executeUpdate();
                    }
                } else {
                    // Si hay nueva contraseña, la encriptamos con BCrypt
                    String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE usuarios SET username=?, password=? WHERE id=?")) {
                        ps.setString(1, username);
                        ps.setString(2, hashed);
                        ps.setInt(3, Integer.parseInt(id));
                        ps.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ListaUsuariosServlet");
    }
}
