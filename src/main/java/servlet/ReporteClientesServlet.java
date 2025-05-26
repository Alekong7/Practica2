package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dto.Cliente;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ReporteClientesServlet", urlPatterns = {"/ReporteClientesServlet"})
public class ReporteClientesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_clientes.pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Título
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Clientes", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" ")); // Espacio

            // Tabla
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Código");
            table.addCell("Apellido Paterno");
            table.addCell("Apellido Materno");
            table.addCell("Nombre");

            // Conexión y consulta
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cripto", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT codiClie, appaClie, apmaClie, nombClie FROM cliente");
            while (rs.next()) {
                table.addCell(rs.getString("codiClie"));
                table.addCell(rs.getString("appaClie"));
                table.addCell(rs.getString("apmaClie"));
                table.addCell(rs.getString("nombClie"));
            }
            document.add(table);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
