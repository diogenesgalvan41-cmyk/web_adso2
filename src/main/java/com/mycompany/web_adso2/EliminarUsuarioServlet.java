package com.mycompany.web_adso2;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "EliminarUsuarioServlet", urlPatterns = {"/EliminarUsuarioServlet"})
public class EliminarUsuarioServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/web_adso2";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                ps.setInt(1, Integer.parseInt(id));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Guardar error en sesión para mostrar en JSP
            session.setAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }

        response.sendRedirect("ListaUsuariosServlet");
    }
}
