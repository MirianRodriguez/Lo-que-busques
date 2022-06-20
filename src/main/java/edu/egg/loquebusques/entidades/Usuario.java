package edu.egg.loquebusques.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "usuario")
@SQLDelete(sql = "UPDATE usuario SET eliminado = true WHERE usuario_id = ?")
@Where(clause = "eliminado = false")

public class Usuario {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "email", length = 60, unique = true, nullable = false)
    private String email;

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "eliminado", nullable = false)
    private boolean eliminado;

    public Usuario() {
        this.eliminado = false;
    }

    public Usuario(Integer id, String email, String contrasenia, Rol rol, boolean eliminado) {
        this.id = id;
        this.email = email;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

}
