package com.mycompany.web_adso2;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ListaUsuariosServlet", urlPatterns = {"/ListaUsuariosServlet"})
public class ListaUsuariosServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/web_adso2";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Map<String, String>> usuarios = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, username, password FROM usuarios")) {

                while (rs.next()) {
                    Map<String, String> usuario = new HashMap<>();
                    usuario.put("id", String.valueOf(rs.getInt("id")));
                    usuario.put("username", rs.getString("username"));
                    usuario.put("password", rs.getString("password"));
                    usuarios.add(usuario);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar con la base de datos: " + e.getMessage());
        }

        // Pasar lista de usuarios al JSP principal
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("bienvenido.jsp").forward(request, response);
    }
}
