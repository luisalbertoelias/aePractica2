/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itt.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.Stateless;

import itt.entities.Usuario;
import itt.utils.Message;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author Elías
 */
@Stateless
public class EJBOperacion {
    
    @PersistenceContext
    private EntityManager em;

    public String consultaPin(int pin){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Query q;
        String result = "";
        Message m = new Message();  
        Usuario upin = null;
        q = em.createNamedQuery("Usuario.findByPin").setParameter("pin",pin); //Para verificar si se encuentra en la bdd
        
        
        try {
            upin = (Usuario)q.getSingleResult();
            m = m.getMessage(m, HttpServletResponse.SC_OK, "La consulta se ejecuto correctamente", gson.toJson(upin));
        } catch (IllegalStateException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (QueryTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (TransactionRequiredException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PessimisticLockException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (LockTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PersistenceException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (EJBException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        }
        result = gson.toJson(m);
        return result;
    }
    
    public String consultaUsuario(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Query q;
        String result = "";
        Message m = new Message();  
        List<Usuario> listUsuario = null;
        q = em.createNamedQuery("Usuario.findAll"); //Para verificar si se encuentra en la bdd
        
        try {
            listUsuario = q.getResultList();
            m = m.getMessage(m, HttpServletResponse.SC_OK, "La consulta se ejecuto correctamente", gson.toJson(listUsuario));
        } catch (IllegalStateException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (QueryTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (TransactionRequiredException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PessimisticLockException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (LockTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PersistenceException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (EJBException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        }
        result = gson.toJson(m);
        return result;
    }
    
    public String guardarUsuario(String unombre, int pin, Date fechaingreso, boolean admin){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String result = "";
        Message m = new Message();  
        Usuario usuario = new Usuario();
        
        try {
            usuario.setUnombre(unombre);
            usuario.setPin(pin);
            usuario.setFechaingreso(fechaingreso);
            usuario.setAdmin(admin);
             
            em.persist(usuario);
            em.flush();
            
            m = m.getMessage(m, HttpServletResponse.SC_OK, "La consulta se ejecuto correctamente", gson.toJson(usuario));
        } catch (IllegalStateException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (QueryTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (TransactionRequiredException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PessimisticLockException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (LockTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PersistenceException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "Error persist", e.toString());
        } catch (EJBException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        }
        result = gson.toJson(m);
        return result;
    }
    
    public String actualizarUsuario(int uid, String unombre){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String result = "";
        Message m = new Message();  
        Usuario usuario = null;
        Query q;
        
        q = em.createNamedQuery("Usuario.findByUid").setParameter("uid", uid);
        
        try {
            
            usuario =(Usuario) q.getSingleResult();
            
            usuario.setUid(uid);
            usuario.setUnombre(unombre);
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
            Date date = new Date();
            dateFormat.format(date);
            
            usuario.setFechaactulizacion(dateFormat.parse(dateFormat.format(date)));
            
            em.merge(usuario);
            em.flush();
            
            m = m.getMessage(m, HttpServletResponse.SC_OK, "La consulta se ejecuto correctamente", gson.toJson(usuario));
        } catch (IllegalStateException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (QueryTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (TransactionRequiredException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PessimisticLockException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (LockTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PersistenceException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (EJBException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (ParseException e){
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        }
        result = gson.toJson(m);
        return result;
    }
    
    
    public String borrarUsuario(int uid){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String result = "";
        Message m = new Message();  
        Usuario usuario = null;
        Query q;
        
        q = em.createNamedQuery("Usuario.findByUid").setParameter("uid", uid);
        
        try {
            
            usuario =(Usuario) q.getSingleResult();
            
            if(usuario == null){
                m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontró usuario", "No se encontró usuario");
            }else{
            em.remove(usuario);
            em.flush();
            
            m = m.getMessage(m, HttpServletResponse.SC_OK, "La consulta se ejecuto correctamente", gson.toJson(usuario));
        }
                      
        } catch (IllegalStateException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (QueryTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (TransactionRequiredException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PessimisticLockException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (LockTimeoutException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (PersistenceException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        } catch (EJBException e) {
            m = m.getMessage(m, HttpServletResponse.SC_BAD_REQUEST, "No se encontro resultado", e.toString());
        }
        result = gson.toJson(m);
        return result;
    }
}
