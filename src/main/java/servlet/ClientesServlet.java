package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClientesServlet", urlPatterns = {"/ClientesServlet"})
public class ClientesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Soporte para caracteres en español
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String accion = request.getParameter("accion");
        String codiClie = request.getParameter("codiClie");
        String appaClie = request.getParameter("appaClie");
        String apmaClie = request.getParameter("apmaClie");
        String nombClie = request.getParameter("nombClie");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Cripto06_war_1.0-SNAPSHOTPU");
        ClienteJpaController dao = new ClienteJpaController(emf);

        try (PrintWriter out = response.getWriter()) {
            if ("registrar".equals(accion)) {
                // Registrar nuevo cliente
                if (codiClie == null || appaClie == null || apmaClie == null || nombClie == null ||
                    codiClie.isEmpty() || appaClie.isEmpty() || apmaClie.isEmpty() || nombClie.isEmpty()) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"Todos los campos son obligatorios.\"}");
                    return;
                }
                Cliente cliente = new Cliente(codiClie, appaClie, apmaClie, nombClie);
                try {
                    dao.create(cliente);
                    out.print("{\"resultado\":\"ok\"}");
                } catch (Exception ex) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"No se pudo registrar el cliente. ¿Código duplicado?\"}");
                }
            } else if ("editar".equals(accion)) {
                // Editar cliente
                if (codiClie == null || appaClie == null || apmaClie == null || nombClie == null ||
                    codiClie.isEmpty() || appaClie.isEmpty() || apmaClie.isEmpty() || nombClie.isEmpty()) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"Todos los campos son obligatorios.\"}");
                    return;
                }
                Cliente cliente = new Cliente(codiClie, appaClie, apmaClie, nombClie);
                try {
                    dao.edit(cliente);
                    out.print("{\"resultado\":\"ok\"}");
                } catch (Exception ex) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"No se pudo editar el cliente.\"}");
                }
            } else if ("eliminar".equals(accion)) {
                // Eliminar cliente
                if (codiClie == null || codiClie.isEmpty()) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"Código requerido.\"}");
                    return;
                }
                try {
                    dao.destroy(codiClie);
                    out.print("{\"resultado\":\"ok\"}");
                } catch (Exception ex) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"No se pudo eliminar el cliente.\"}");
                }
            } else {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"Acción no válida.\"}");
            }
        }
    }
}
