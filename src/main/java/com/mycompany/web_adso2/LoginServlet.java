package com.mycompany.web_adso2;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt; // Importar BCrypt

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String DB_URL = "jdbc:mysql://localhost:3306/web_adso2"; // tu base de datos
    private final String DB_USER = "root"; // usuario de MySQL
    private final String DB_PASS = "";     // contraseña de MySQL (si tienes)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean autenticado = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("SELECT password FROM usuarios WHERE username=?")) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String hash = rs.getString("password");
                    // Comparar contraseña ingresada con el hash guardado
                    autenticado = BCrypt.checkpw(password, hash);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // se verá en catalina.out si hay error
        }

        if (autenticado) {
            // Guardar sesión
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // Redirigir al servlet que carga la lista
            response.sendRedirect("ListaUsuariosServlet");
        } else {
            // Volver al login con mensaje de error
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
