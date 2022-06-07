package edu.egg.loquebusques.entidades;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "categoria")
@SQLDelete(sql = "UPDATE categoria SET eliminado = true WHERE categoria_id = ?") 
@Where(clause = "eliminado = false")

public class Categoria{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition="BLOB", nullable = false)
    private String descripcion;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    public Categoria() {
        this.eliminado = false;
    }

    public Categoria(Integer id, String nombre, String descripcion, Boolean eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }


}





