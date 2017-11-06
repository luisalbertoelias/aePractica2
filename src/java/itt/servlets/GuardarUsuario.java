/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itt.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itt.ejbs.EJBOperacion;
import itt.utils.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Elías
 */
@WebServlet(name = "GuardarUsuario", urlPatterns = {"/GuardarUsuario"})
public class GuardarUsuario extends HttpServlet {

    @EJB
    private EJBOperacion ejb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter p = response.getWriter();

        String unombre = request.getParameter("unombre");
        int pin = Integer.parseInt(request.getParameter("pin"));

//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        //Date fechaingreso = dateFormat.parse(request.getParameter("fechaingreso"));
//            Date fechaingreso = dateFormat.parse(request.getParameter("fechaingreso"));
        boolean admin = Boolean.parseBoolean(request.getParameter("admin"));

        p.print(ejb.guardarUsuario(unombre, pin, admin));

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
