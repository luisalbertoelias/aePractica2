/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itt.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Elías
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUid", query = "SELECT u FROM Usuario u WHERE u.uid = :uid")
    , @NamedQuery(name = "Usuario.findByUnombre", query = "SELECT u FROM Usuario u WHERE u.unombre = :unombre")
    , @NamedQuery(name = "Usuario.findByPin", query = "SELECT u FROM Usuario u WHERE u.pin = :pin")
    , @NamedQuery(name = "Usuario.findByFechaingreso", query = "SELECT u FROM Usuario u WHERE u.fechaingreso = :fechaingreso")
    , @NamedQuery(name = "Usuario.findByAdmin", query = "SELECT u FROM Usuario u WHERE u.admin = :admin")
    , @NamedQuery(name = "Usuario.findByFechaactulizacion", query = "SELECT u FROM Usuario u WHERE u.fechaactulizacion = :fechaactulizacion")
    , @NamedQuery(name = "Usuario.findByPermiso", query = "SELECT u FROM Usuario u WHERE u.permiso = :permiso")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uid")
    private Integer uid;
    @Size(max = 50)
    @Column(name = "unombre")
    private String unombre;
    @Column(name = "pin")
    private Integer pin;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Column(name = "admin")
    private Boolean admin;
    @Column(name = "fechaactulizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaactulizacion;
    @Column(name = "permiso")
    private Boolean permiso;

    public Usuario() {
    }

    public Usuario(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUnombre() {
        return unombre;
    }

    public void setUnombre(String unombre) {
        this.unombre = unombre;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Date getFechaactulizacion() {
        return fechaactulizacion;
    }

    public void setFechaactulizacion(Date fechaactulizacion) {
        this.fechaactulizacion = fechaactulizacion;
    }

    public Boolean getPermiso() {
        return permiso;
    }

    public void setPermiso(Boolean permiso) {
        this.permiso = permiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "itt.entities.Usuario[ uid=" + uid + " ]";
    }
    
}
