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
@WebServlet(name = "EliminarUsuario", urlPatterns = {"/EliminarUsuario"})
public class EliminarUsuario extends HttpServlet {

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
        response.setHeader("Cache-Control","no-store");
        PrintWriter p = response.getWriter();
        int uid = request.getParameter("uid") == null || request.getParameter("uid").isEmpty() ? 0 
                : Integer.parseInt(request.getParameter("uid"));
        if(uid == 0){
            Message m = new Message();
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("Not found the user.");
            m.setDetail("The parameter sended is incorrect.");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            p.print(gson.toJson(m));
        }else{
            p.print(ejb.borrarUsuario(uid));
        }
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
