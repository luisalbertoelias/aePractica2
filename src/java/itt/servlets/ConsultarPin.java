/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itt.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import itt.ejbs.EJBOperacion;

/**
 *
 * @author Elías
 */
@WebServlet(name = "ConsultarPin", urlPatterns = {"/ConsultarPin"})
public class ConsultarPin extends HttpServlet {

    @EJB
    private EJBOperacion ejb;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        //Obligar al cliente HTTP a que no guarde su valor en cache
        //Crear el objecto necesario para devolver la respuesta
        PrintWriter p = response.getWriter();
        //Devolver la respuesta 
        
        int upin = Integer.parseInt(request.getParameter("upin"));
        p.print(ejb.consultaPin(upin));
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
