
package servlet;

import com.google.gson.Gson;
import dao.ProductoJpaController;
import dto.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ApiProducto", urlPatterns = {"/producto"})
public class ApiProducto extends HttpServlet {

    ProductoJpaController productoDAO = new ProductoJpaController();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Producto> productos = productoDAO.findProductoEntities();
        String json = new Gson().toJson(productos);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
