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
@WebServlet(name = "ActualizarUsuario", urlPatterns = {"/ActualizarUsuario"})
public class ActualizarUsuario extends HttpServlet {

    @EJB
    private EJBOperacion ejb;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter p = response.getWriter();
        Message m = new Message();
        m = m.getMessage(m,405, "Error al realizar la consulta", "Method not allowed");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        p.print(gson.toJson(m));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setHeader("Cache-Control", "no-store");
        response.setContentType("application/json;charset=UTF-8");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        PrintWriter p = response.getWriter();
        
        int uid = Integer.parseInt(request.getParameter("uid"));
        String unombre = request.getParameter("unombre");
        
        p.print(ejb.actualizarUsuario(uid,unombre));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
